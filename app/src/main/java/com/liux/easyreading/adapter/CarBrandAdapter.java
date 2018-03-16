package com.liux.easyreading.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.liux.easyreading.R;
import com.liux.module.carBean.CarBean;

import java.util.List;


/**
 * Created by
 * 项目名称：com.liux.easyreading.adapter
 * 项目日期：2017/12/1
 * 作者：liux
 * 功能：
 * @author 75095
 */

public class CarBrandAdapter extends BaseQuickAdapter<CarBean.ResultBean.SonBean,BaseViewHolder> {

    public CarBrandAdapter(@LayoutRes int layoutResId, @Nullable List<CarBean.ResultBean.SonBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CarBean.ResultBean.SonBean item) {
        helper.setText(R.id.tv_car_brand,item.getCar())
                .setText(R.id.tv_car_name,item.getType());
    }
}
