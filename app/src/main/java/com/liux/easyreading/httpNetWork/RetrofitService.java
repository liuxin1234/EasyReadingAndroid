package com.liux.easyreading.httpNetWork;

import com.liux.module.GankBean.BaseBean;
import com.liux.module.GankBean.ResultBean;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

/**
 * Created by
 * 项目名称：com.liux.easyreading.httpNetWork
 * 项目日期：2017/12/26
 * 作者：liux
 * 功能：
 *
 * @author 750954283(qq)
 */

public interface RetrofitService {
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @GET("data/{type}/20/{page}")
    Call<BaseBean<List<ResultBean>>> getNewsList(@Path("type") String type,@Path("page") int page);
}
