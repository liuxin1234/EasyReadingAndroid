package com.liux.easyreading.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.google.gson.Gson;
import com.liux.easyreading.R;
import com.liux.easyreading.activity.WebViewActivity;
import com.liux.easyreading.adapter.WXPageAdapter;
import com.liux.easyreading.base.BaseFragment;
import com.liux.easyreading.utils.GsonUtils;
import com.liux.easyreading.view.DividerItemDecoration;
import com.liux.module.wxBean.ArticleSearchBean;
import com.liux.module.wxBean.CategoriesBean;
import com.mob.mobapi.API;
import com.mob.mobapi.APICallback;
import com.mob.mobapi.apis.WxArticle;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by
 * 项目名称：com.liux.easyreading.fragment
 * 项目日期：2017/11/3
 * 作者：liux
 * 功能：
 * @author 75095
 */

public class PageFragment extends BaseFragment implements APICallback {

    private static final String KEY = "EXTRA";
    @BindView(R.id.recycle_View)
    RecyclerView recycleView;
    @BindView(R.id.refresh_layout)
    MaterialRefreshLayout refreshLayout;

    private WXPageAdapter mWXPageAdapter;
    private int pageIndex = 0;
    private static final int PAGE_SIZE = 20;
    private String cid;
    private boolean isLoadMore = false;

    private CategoriesBean.ResultBean bean;
    private List<ArticleSearchBean.ResultBean.ListBean> mBeanList = new ArrayList<>();

    public static PageFragment newInstance(CategoriesBean.ResultBean bean) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY, bean);
        PageFragment pageFragment = new PageFragment();
        pageFragment.setArguments(bundle);
        return pageFragment;
    }

    @Override
    public int setLayout() {
        return R.layout.fragment_page;
    }

    @Override
    public void initView() {
        initAdapter();
        initRefresh();
    }

    @Override
    protected void loadData() {
        /**
         * 这里加载数据是为了让用户更快的看见数据，增加体验性，但是注意上面initView中
         * initRefresh（）方法使用了自动下拉刷新，会出现2次加载数据的方法。
         * 若是为了节省流量，可以将initRefresh（）放到这里，删除initData()方法
         */
        initData();
    }

    private void initRefresh() {
        refreshLayout.setLoadMore(true);
        refreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                materialRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isLoadMore = false;
                        pageIndex = 0; //设置pageIndex=0，是为了让下拉刷新数据重新从第一页开始
                        initData();
                        refreshLayout.finishRefresh();
                    }
                },3000);
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                materialRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isLoadMore = true;
                        pageIndex++;
                        initData();
                        refreshLayout.finishRefreshLoadMore();
                    }
                },3000);
            }
        });
        refreshLayout.autoRefresh();
    }

    private void initAdapter() {
        mWXPageAdapter = new WXPageAdapter(getActivity(),R.layout.item_layout_wxpage,mBeanList);
        recycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycleView.addItemDecoration(new DividerItemDecoration(getActivity(),1));
        recycleView.setAdapter(mWXPageAdapter);
        mWXPageAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("url",mBeanList.get(position).getSourceUrl());
                startActivity(intent);
            }
        });
    }

    private void initData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            bean = (CategoriesBean.ResultBean) bundle.getSerializable(KEY);
            if (bean != null) {
                cid = bean.getCid();
            }
        }

        WxArticle wxArticle = new WxArticle();
        wxArticle.searchArticleList(cid, pageIndex + 1, PAGE_SIZE, this);

    }

    @Override
    public void onSuccess(API api, int i, Map<String, Object> map) {
        GsonUtils.toJson(map);
        ArticleSearchBean articleSearchBean = GsonUtils.object(map, ArticleSearchBean.class);
        List<ArticleSearchBean.ResultBean.ListBean> listBeen = articleSearchBean.getResult().getList();
        if (isLoadMore){
            //上拉加载 addAll
            mBeanList.addAll(listBeen);
        }else {
            //下拉刷新 先clear,再addAll
            mBeanList.clear();
            mBeanList.addAll(listBeen);
        }
        mWXPageAdapter.notifyDataSetChanged();
    }

    @Override
    public void onError(API api, int i, Throwable throwable) {

    }


}
