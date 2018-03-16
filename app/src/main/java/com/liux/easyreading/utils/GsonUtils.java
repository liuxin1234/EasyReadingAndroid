package com.liux.easyreading.utils;

import com.google.gson.Gson;
import com.liux.module.historyTodayBean.historyBean;
import com.orhanobut.logger.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by
 * 项目名称：com.liux.easyreading.utils
 * 项目日期：2017/11/6
 * 作者：liux
 * 功能：
 * @author 75095
 */

public class GsonUtils {
    public static <T> T object(Map<String,Object> map, Class<T> tClass){
        Gson gson = new Gson();
        String json = gson.toJson(map);
        Logger.json(json);
        return gson.fromJson(json, tClass);
    }

    public static <T> String toJson(Object object){
        Gson gson = new Gson();
        Logger.json(gson.toJson(object));
        return gson.toJson(object);
    }

}
