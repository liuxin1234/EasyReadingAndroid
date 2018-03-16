package com.liux.easyreading.utils;


import com.liux.easyreading.R;

/**
 * Created by
 * 项目名称：com.liux.weather.utils
 * 项目日期：2017/9/12
 * 作者：liux
 * 功能：天气小图标的转换工具
 * {bigpour：特大暴雨，cloud：多云，cloudn：夜间多云，cloudy：晴转多云，cloudyn：，fog：雾，heavyrain：暴雨，
 * heavysnow：暴雪，largepour：大雨，largesnow：大雪，lightrain：小雨，lightsnow：小雪，midrain：中雨
 * midsnow：中雪，pour：大暴雨，shower：，showern：，sun：天晴，sunn：，thundershower：雷阵雨，tianqi：天晴（有云）}
 */

public class weatherUtil {


    public weatherUtil() {

    }

    public static int setImgResource(String s){
        int Resource= R.mipmap.cloud;
        if ("阴".equals(s)){
            Resource = R.mipmap.cloud;
        }
        if ("多云".equals(s) || "少云".equals(s) ||"局部多云".equals(s)) {
            Resource = R.mipmap.cloudy;
        }
        if ("晴".equals(s)){
            Resource = R.mipmap.sun_110;
        }
        if ("阵雨".equals(s) || "雷阵雨".equals(s)){
            Resource = R.mipmap.thundershower;
        }
        if ("零散阵雨".equals(s) || "零散雷雨".equals(s)){
            Resource = R.mipmap.thundershower;
        }
        if ("小雨".equals(s)){
            Resource = R.mipmap.lightrain;
        }
        if ("中雨".equals(s) || "雨".equals(s) ) {
            Resource = R.mipmap.midrain;
        }
        if ("暴雨".equals(s)) {
            Resource = R.mipmap.heavyrain;
        }
        if ("大暴雨".equals(s)){
            Resource = R.mipmap.pour;
        }
        if ("特大暴雨".equals(s)){
            Resource = R.mipmap.bigpour;
        }
        if ("小雪".equals(s)){
            Resource = R.mipmap.lightsnow;
        }
        if ("中雪".equals(s)){
            Resource = R.mipmap.midsnow;
        }
        if ("大雪".equals(s)){
            Resource = R.mipmap.heavysnow;
        }
        if ("大暴雪".equals(s)){
            Resource = R.mipmap.largesnow;
        }
        if ("雨夹雪".equals(s) || "阵雪".equals(s)){
            Resource = R.mipmap.midsnow;
        }
        if ("雾".equals(s) || "霾".equals(s)){
            Resource = R.mipmap.fog;
        }

        return Resource;
    }


    public static String setTime(String i){
        String time = null;
        if ("1".equals(i)){
            time = "星期一";
        }
        if ("2".equals(i)){
            time = "星期二";
        }
        if ("3".equals(i)){
            time = "星期三";
        }
        if ("4".equals(i)){
            time = "星期四";
        }
        if ("5".equals(i)){
            time = "星期五";
        }
        if ("6".equals(i)){
            time = "星期六";
        }
        if ("7".equals(i)){
            time = "星期七";
        }

        return time;
    }

}
