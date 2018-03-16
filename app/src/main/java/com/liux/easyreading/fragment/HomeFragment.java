package com.liux.easyreading.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

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

/**
 * Created by
 * 项目名称：com.liux.easyreading.fragment
 * 项目日期：2017/11/3
 * 作者：liux
 * 功能：
 *
 * @author 75095
 */

public class HomeFragment extends BaseFragment implements APICallback {

    private static final String KEY = "EXTRA";
    @BindView(R.id.recycle_View)
    RecyclerView recycleView;
    @BindView(R.id.refresh_layout)
    MaterialRefreshLayout refreshLayout;

    private String cid;
    private int pageIndex = 0;
    private static final int PAGE_SIZE = 20;
    /**
     * 在上拉刷新的时候，判断，是否处于上拉刷新，如果是的话，就禁止在一次刷新，保障在数据加载完成之前
     * 避免重复和多次加载
     */
    private boolean isLoadMore = false;

    private CategoriesBean.ResultBean mResultBean;

    private List<ArticleSearchBean.ResultBean.ListBean> mResultBeanList = new ArrayList<>();

    private WXPageAdapter mWXPageAdapter;

    public static HomeFragment newInstance(CategoriesBean.ResultBean bean) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY, bean);
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int setLayout() {
        return R.layout.fragment_home;
    }

    @Override
    public void initView() {
        initAdapter();
    }

    @Override
    protected void loadData() {
        initRefreshData();
    }

    /**
     * 初始化加载
     */
    private void initRefreshData() {

        refreshLayout.setLoadMore(true);

        refreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                materialRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isLoadMore = false;
                        pageIndex = 0;
                        initData();
                        refreshLayout.finishRefresh();
                    }
                },3000);
            }

            @Override
            public void onRefreshLoadMore(final MaterialRefreshLayout materialRefreshLayout) {
                materialRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isLoadMore = true;
                        pageIndex++;
                        initData();
                        materialRefreshLayout.finishRefreshLoadMore();
                    }
                },3000);
            }
        });

        refreshLayout.autoRefresh();
    }

    private void initAdapter() {
        mWXPageAdapter = new WXPageAdapter(getActivity(),R.layout.item_layout_wxpage, mResultBeanList);
        recycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycleView.addItemDecoration(new DividerItemDecoration(getActivity(),1));
        recycleView.setAdapter(mWXPageAdapter);
        mWXPageAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("url",mResultBeanList.get(position).getSourceUrl());
                startActivity(intent);
            }
        });

    }

    private void initData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mResultBean = (CategoriesBean.ResultBean) bundle.getSerializable(KEY);
            if (mResultBean != null) {
                cid = mResultBean.getCid();
            }
        }
        WxArticle wxArticle = new WxArticle();
        wxArticle.searchArticleList(cid, pageIndex + 1, PAGE_SIZE, this);
    }

    @Override
    public void onSuccess(API api, int i, Map<String, Object> map) {
        GsonUtils.toJson(map);
        ArticleSearchBean articleSearchBean = GsonUtils.object(map,ArticleSearchBean.class);
        ArticleSearchBean.ResultBean result = articleSearchBean.getResult();
        List<ArticleSearchBean.ResultBean.ListBean> resultList = result.getList();
        if (isLoadMore){
            //上拉加载 addAll
            mResultBeanList.addAll(resultList);
        }else {
            //下拉刷新 先clear,再addAll
            mResultBeanList.clear();
            mResultBeanList.addAll(resultList);
        }
        mWXPageAdapter.notifyDataSetChanged();
    }

    @Override
    public void onError(API api, int i, Throwable throwable) {

    }

}
