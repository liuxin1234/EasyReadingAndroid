package com.liux.module.smartRefreshBean;

/**
 * Created by
 * 项目名称：com.liux.module.smartRefreshBean
 * 项目日期：2017/12/6
 * 作者：liux
 * 功能：
 */

public class SmartRefreshBaseBean {

    private String imgUrl;
    private String title;

    public SmartRefreshBaseBean(String imgUrl, String title) {
        this.imgUrl = imgUrl;
        this.title = title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
