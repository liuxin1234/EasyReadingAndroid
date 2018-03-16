package com.liux.easyreading.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.liux.easyreading.R;
import com.liux.module.carBean.CarSeriesNameBean;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by
 * 项目名称：com.liux.easyreading.adapter
 * 项目日期：2017/12/4
 * 作者：liux
 * 功能：
 */

public class CarSeriesNameAdapter extends BaseQuickAdapter<CarSeriesNameBean.ResultBean,BaseViewHolder> {

    private Context mContext;
    private List<CarSeriesNameBean.ResultBean> mResultBeanList = new ArrayList<>();


    public CarSeriesNameAdapter(Context context,@LayoutRes int layoutResId, @Nullable List<CarSeriesNameBean.ResultBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CarSeriesNameBean.ResultBean item) {
        helper.setText(R.id.tv_car_name,item.getSeriesName())
                .setText(R.id.tv_car_id,item.getCarId())
                .setText(R.id.tv_guidance_price,item.getGuidePrice())
                .setText(R.id.tv_car_model_name,item.getBrandName());
    }
}
