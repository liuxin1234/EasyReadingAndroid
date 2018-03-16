package com.liux.easyreading.eventBus;

/**
 * Created by
 * 项目名称：com.liux.easyreading.eventBus
 * 项目日期：2017/11/7
 * 作者：liux
 * 功能：
 */

public class LoginEvent {
    private boolean isLogin;
    private String userName;

    public LoginEvent(boolean isLogin,String userName){
        this.isLogin = isLogin;
        this.userName = userName;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
