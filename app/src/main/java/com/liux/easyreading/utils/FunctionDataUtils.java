package com.liux.easyreading.utils;

import com.liux.easyreading.R;
import com.liux.module.moreFunctionBean.FunctionBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by
 * 项目名称：com.liux.easyreading.utils
 * 项目日期：2017/11/6
 * 作者：liux
 * 功能：
 * @author 75095
 */

public class FunctionDataUtils {

    public static List<FunctionBean> getItemData(){
        List<FunctionBean> beanList = new ArrayList<FunctionBean>();
        beanList.add(new FunctionBean(R.color.tribe_host,R.drawable.icon_shoucang,"历史上的今天"));
        beanList.add(new FunctionBean(R.color.colorPrimary,R.drawable.icon_shoucang,"天气预报"));
        beanList.add(new FunctionBean(R.color.toast_color,R.drawable.icon_shoucang,"百度地图"));
        beanList.add(new FunctionBean(R.color.lawngreen,R.drawable.icon_shoucang,"空气质量"));
        beanList.add(new FunctionBean(R.color.mask_tags_1,R.drawable.icon_shoucang,"新华字典"));
        beanList.add(new FunctionBean(R.color.mask_tags_2,R.drawable.icon_shoucang,"汽车信息"));
        beanList.add(new FunctionBean(R.color.mask_tags_3,R.drawable.icon_shoucang,"智能刷新"));
        beanList.add(new FunctionBean(R.color.mask_tags_4,R.drawable.icon_shoucang,"美图福利"));
        return beanList;
    }
}
