package com.liux.easyreading.activity.moreFunction;

import android.widget.TextView;

import com.liux.easyreading.R;
import com.liux.easyreading.base.BaseActivity;
import com.liux.easyreading.utils.DateUtils;
import com.liux.easyreading.view.ToolbarHelper;
import com.liux.module.historyTodayBean.historyBean;

import java.io.Serializable;

import butterknife.BindView;

/**
 * Created by
 * 项目名称：com.liux.easyreading.activity.moreFunction
 * 项目日期：2017/11/6
 * 作者：liux
 * 功能：历史上的今天详情页
 *
 * @author 75095
 */

public class HistoryDetailActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_event)
    TextView tvEvent;
    @BindView(R.id.tv_history_today)
    TextView tvHistoryToday;
    private historyBean.ResultBean mResultBean;

    @Override
    protected void initToolbar(ToolbarHelper toolbarHelper) {
        toolbarHelper.setTitle("历史上的今天");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_history_detial;
    }

    @Override
    protected void initView() {
        initData();
    }

    private void initData() {
        Serializable dataList = getIntent().getSerializableExtra("dataList");
        if (dataList != null) {
            mResultBean = (historyBean.ResultBean) dataList;
        }
        tvTitle.setText(mResultBean.getTitle());
        tvEvent.setText(mResultBean.getEvent());
        tvHistoryToday.setText(DateUtils.toLongYMD(mResultBean.getDate()));
    }

}
