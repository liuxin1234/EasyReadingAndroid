package com.liux.module.moreFunctionBean;

import java.io.Serializable;

/**
 * Created by
 * 项目名称：com.liux.module.moreFunctionBean
 * 项目日期：2017/11/6
 * 作者：liux
 * 功能：
 */

public class FunctionBean implements Serializable {
    private int icoBgColor;
    private int icoName;
    private String activityTitle;


    public FunctionBean() {

    }

    public FunctionBean(int icoBgColor, int icoName, String activityTitle) {
        this.icoBgColor = icoBgColor;
        this.icoName = icoName;
        this.activityTitle = activityTitle;
    }

    public int getIcoBgColor() {
        return icoBgColor;
    }

    public void setIcoBgColor(int icoBgColor) {
        this.icoBgColor = icoBgColor;
    }

    public int getIcoName() {
        return icoName;
    }

    public void setIcoName(int icoName) {
        this.icoName = icoName;
    }

    public String getActivityTitle() {
        return activityTitle;
    }

    public void setActivityTitle(String activityTitle) {
        this.activityTitle = activityTitle;
    }
}
