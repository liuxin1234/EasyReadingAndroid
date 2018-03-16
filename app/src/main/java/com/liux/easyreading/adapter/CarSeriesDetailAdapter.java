package com.liux.easyreading.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.liux.easyreading.R;
import com.liux.module.carBean.CarSeriesDetailBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by
 * 项目名称：com.liux.easyreading.adapter
 * 项目日期：2017/12/4
 * 作者：liux
 * 功能：
 */

public class CarSeriesDetailAdapter extends BaseQuickAdapter<Map<String, Object>,BaseViewHolder> {

    private Context mContext;

    public CarSeriesDetailAdapter(Context context,@LayoutRes int layoutResId, @Nullable List<Map<String, Object>> data) {
        super(layoutResId, data);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, Map<String, Object> item) {
        helper.setText(R.id.tv_Name,item.get("name").toString())
                .setText(R.id.tv_Info,item.get("info").toString());
    }
}
