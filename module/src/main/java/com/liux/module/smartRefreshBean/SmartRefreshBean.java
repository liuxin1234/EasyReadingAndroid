package com.liux.module.smartRefreshBean;

/**
 * Created by
 * 项目名称：com.liux.module.smartRefreshBean
 * 项目日期：2017/12/6
 * 作者：liux
 * 功能：
 */

public class SmartRefreshBean {

    private String enName; //英文名字

    private String cnName; //中文名字


    public SmartRefreshBean(String enName, String cnName) {
        this.enName = enName;
        this.cnName = cnName;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getCnName() {
        return cnName;
    }

    public void setCnName(String cnName) {
        this.cnName = cnName;
    }

    @Override
    public String toString() {
        return "SmartRefreshBean{" +
                "enName='" + enName + '\'' +
                ", cnName='" + cnName + '\'' +
                '}';
    }
}
