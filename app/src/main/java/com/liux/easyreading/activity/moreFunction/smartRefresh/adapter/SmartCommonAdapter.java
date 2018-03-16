package com.liux.easyreading.activity.moreFunction.smartRefresh.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.liux.easyreading.R;
import com.liux.module.smartRefreshBean.SmartRefreshBaseBean;

import java.util.List;

/**
 * Created by
 * 项目名称：com.liux.easyreading.activity.moreFunction.smartRefresh
 * 项目日期：2017/12/6
 * 作者：liux
 * 功能：
 * @author 75095
 */

public class SmartCommonAdapter extends BaseQuickAdapter<SmartRefreshBaseBean,BaseViewHolder> {


    public SmartCommonAdapter(@LayoutRes int layoutResId, @Nullable List<SmartRefreshBaseBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SmartRefreshBaseBean item) {
        helper.setText(R.id.tv_title,item.getTitle());
    }
}
