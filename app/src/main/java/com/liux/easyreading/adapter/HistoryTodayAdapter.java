package com.liux.easyreading.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.liux.easyreading.R;
import com.liux.easyreading.utils.DateUtils;
import com.liux.module.historyTodayBean.historyBean;

import java.util.List;

/**
 * Created by
 * 项目名称：com.liux.easyreading.adapter
 * 项目日期：2017/11/6
 * 作者：liux
 * 功能：
 * @author 75095
 */

public class HistoryTodayAdapter extends BaseQuickAdapter<historyBean.ResultBean,BaseViewHolder> {

    public HistoryTodayAdapter(@LayoutRes int layoutResId, @Nullable List data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder holder, historyBean.ResultBean item) {
        String date = item.getDate();
        DateUtils.toLongYMD(date);

        holder.setText(R.id.tv_title,item.getTitle())
                .setText(R.id.tv_event,item.getEvent())
                .setText(R.id.tv_history_today,DateUtils.toLongYMD(date));
    }
}
