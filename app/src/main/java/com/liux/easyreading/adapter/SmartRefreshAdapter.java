package com.liux.easyreading.adapter;

import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.liux.easyreading.R;
import com.liux.module.smartRefreshBean.SmartRefreshBean;

import java.util.List;

/**
 * Created by
 * 项目名称：com.liux.easyreading.adapter
 * 项目日期：2017/12/6
 * 作者：liux
 * 功能：
 * @author 75095
 */

public class SmartRefreshAdapter extends BaseQuickAdapter<SmartRefreshBean,BaseViewHolder> {

    public SmartRefreshAdapter(@LayoutRes int layoutResId, @Nullable List<SmartRefreshBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SmartRefreshBean item) {
        helper.setText(android.R.id.text1,item.getEnName())
                .setText(android.R.id.text2,item.getCnName())
                .setTextColor(android.R.id.text2, Color.GRAY);
    }
}
