package com.liux.easyreading.activity.moreFunction;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.liux.easyreading.R;
import com.liux.easyreading.adapter.FuLiAdapter;
import com.liux.easyreading.base.BaseActivity;
import com.liux.easyreading.httpNetWork.Api.ApiConstants;
import com.liux.easyreading.httpNetWork.Api.HostType;
import com.liux.easyreading.httpNetWork.RetrofitManager;
import com.liux.easyreading.httpNetWork.RetrofitService;
import com.liux.easyreading.utils.GsonUtils;
import com.liux.easyreading.utils.ToastUtil;
import com.liux.easyreading.view.ToolbarHelper;
import com.liux.module.GankBean.BaseBean;
import com.liux.module.GankBean.ResultBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by
 * 项目名称：com.liux.easyreading.activity.moreFunction
 * 项目日期：2017/12/26
 * 作者：liux
 * 功能：美图福利
 *
 * @author 750954283(qq)
 */

public class FuLiActivity extends BaseActivity {
    @BindView(R.id.recycle_View)
    RecyclerView recycleView;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    private RetrofitService mRetrofitService;
    private int pageIndex = 1;
    private FuLiAdapter mFuLiAdapter;
    private List<ResultBean> mList = new ArrayList<>();

    @Override
    protected void initToolbar(ToolbarHelper toolbarHelper) {
        toolbarHelper.setTitle("美图福利");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_fuli;
    }

    @Override
    protected void initView() {
        initData();
        initAdapter();
    }

    private void initAdapter() {
        mFuLiAdapter = new FuLiAdapter(this,R.layout.item_layout_fuli,mList);
        recycleView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        recycleView.setAdapter(mFuLiAdapter);
        mFuLiAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ToastUtil.showToast(FuLiActivity.this,""+position);
            }
        });
    }

    private void initData() {
        mRetrofitService = RetrofitManager.getInstance(HostType.GANK_IO_DATA).getRetrofitService();
        mRetrofitService.getNewsList(ApiConstants.GANDK_IO_MEIZI, pageIndex).enqueue(new Callback<BaseBean<List<ResultBean>>>() {
            @Override
            public void onResponse(@NonNull Call<BaseBean<List<ResultBean>>> call, @NonNull Response<BaseBean<List<ResultBean>>> response) {
                if (response.isSuccessful()) {
                    BaseBean<List<ResultBean>> body = response.body();
                    if (body != null) {
                        List<ResultBean> results = body.getResults();
                        GsonUtils.toJson(results);
                        mList.clear();
                        mList.addAll(results);
                        mFuLiAdapter.notifyDataSetChanged();
                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<BaseBean<List<ResultBean>>> call, @NonNull Throwable t) {

            }
        });
    }


    @OnClick(R.id.fab)
    public void onViewClicked() {
    }
}
