package com.liux.easyreading.httpNetWork.Api;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.liux.easyreading.httpNetWork.Api.HostType.JU_HE_NEWS;

/**
 * Created by
 * 项目名称：com.liux.easyreading.httpNetWork.Api
 * 项目日期：2017/12/26
 * 作者：liux
 * 功能：
 *
 * @author 750954283(qq)
 */

public class HostType {
    /**
     * 多少种Host类型
     */
    public static final int TYPE_COUNT = 3;

    /**
     * 聚合数据的host
     */
    public static final int JU_HE_NEWS = 1;

    /**
     * 干货的host
     */
    public static final int GANK_IO_DATA= 2;

    /**
     * 新闻详情html图片的host
     */
    public static final int NEWS_DETAIL_HTML_PHOTO = 3;

    /**
     * 替代枚举的方案，使用IntDef保证类型安全
     */
    @IntDef({JU_HE_NEWS,GANK_IO_DATA,NEWS_DETAIL_HTML_PHOTO})
    @Retention(RetentionPolicy.SOURCE)
    public @interface HostTypeChecker{}
}
