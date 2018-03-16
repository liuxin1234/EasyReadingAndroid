package com.liux.easyreading.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.liux.easyreading.R;
import com.liux.easyreading.utils.weatherUtil;
import com.liux.module.weatherBean.WeatherBean;
import com.orhanobut.logger.Logger;

import java.util.List;

/**
 * Created by
 * 项目名称：com.liux.easyreading.adapter
 * 项目日期：2017/11/9
 * 作者：liux
 * 功能：
 * @author 75095
 */

public class WeatherAdapter extends BaseQuickAdapter<WeatherBean.ResultBean.FutureBean,BaseViewHolder> {



    public WeatherAdapter(@LayoutRes int layoutResId, @Nullable List<WeatherBean.ResultBean.FutureBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, WeatherBean.ResultBean.FutureBean item) {
        String temperature = item.getTemperature();
        String[] strings = temperature.split("/");
        holder.setText(R.id.tv_Week,item.getWeek())
                .setText(R.id.tv_MaxTmp,strings[0])
                .setText(R.id.tv_MinTmp,strings[1])
                .setImageResource(R.id.img_Weather, weatherUtil.setImgResource(item.getDayTime()));
    }

}
