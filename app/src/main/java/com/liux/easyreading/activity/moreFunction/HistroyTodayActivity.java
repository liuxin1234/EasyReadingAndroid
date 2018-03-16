package com.liux.easyreading.activity.moreFunction;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.liux.easyreading.R;
import com.liux.easyreading.adapter.HistoryTodayAdapter;
import com.liux.easyreading.base.BaseActivity;
import com.liux.easyreading.base.BaseSwipeBackActivity;
import com.liux.easyreading.utils.DateUtils;
import com.liux.easyreading.utils.GsonUtils;
import com.liux.easyreading.view.ToolbarHelper;
import com.liux.module.historyTodayBean.historyBean;
import com.mob.mobapi.API;
import com.mob.mobapi.APICallback;
import com.mob.mobapi.apis.History;
import com.orhanobut.logger.Logger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by
 * 项目名称：com.liux.easyreading.activity.moreFunction
 * 项目日期：2017/11/6
 * 作者：liux
 * 功能：历史上的今天
 *
 * @author 75095
 */

public class HistroyTodayActivity extends BaseSwipeBackActivity {

    @BindView(R.id.rv_historyToday)
    RecyclerView rvHistoryToday;
    @BindView(R.id.refresh_layout)
    MaterialRefreshLayout refreshLayout;
    private HistoryTodayAdapter mHistoryTodayAdapter;

    private List<historyBean.ResultBean> mResultBeen = new ArrayList<historyBean.ResultBean>();

    private boolean isShowLoadMore = false;


    @Override
    protected void initToolbar(ToolbarHelper toolbarHelper) {
        toolbarHelper.setTitle("历史上的今天");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_history_today;
    }

    @Override
    protected void initView() {
        initData();
        initAdapter();
        initRefresh();
    }

    private void initRefresh() {
        refreshLayout.setLoadMore(isShowLoadMore);
        refreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                materialRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        initData();
                        refreshLayout.finishRefresh();
                    }
                },3000);
            }

        });
        refreshLayout.autoRefresh();
    }

    private void initData() {
        String shortMD = DateUtils.getShortMD();

        History history = new History();
        history.queryHistory(shortMD, new APICallback() {
            @Override
            public void onSuccess(API api, int i, Map<String, Object> map) {
                historyBean historyBean = GsonUtils.object(map, historyBean.class);
                List<historyBean.ResultBean> result = historyBean.getResult();
                mResultBeen.addAll(result);
                mHistoryTodayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(API api, int i, Throwable throwable) {

            }
        });
    }

    private void initAdapter() {
        mHistoryTodayAdapter = new HistoryTodayAdapter(R.layout.item_layout_history_today, mResultBeen);
        rvHistoryToday.setLayoutManager(new LinearLayoutManager(this));
        rvHistoryToday.setAdapter(mHistoryTodayAdapter);
        mHistoryTodayAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(HistroyTodayActivity.this, HistoryDetailActivity.class);
                intent.putExtra("dataList", (Serializable) mResultBeen.get(position));
                startActivity(intent);
            }
        });
    }


}
