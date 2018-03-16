package com.liux.easyreading.utils;

import android.content.Context;
import android.view.ViewGroup;

import com.liux.easyreading.activity.MainActivity;
import com.liux.easyreading.common.SDKCommonKey;

import net.youmi.android.AdManager;
import net.youmi.android.nm.sp.SplashViewSettings;
import net.youmi.android.nm.sp.SpotListener;
import net.youmi.android.nm.sp.SpotManager;

/**
 * Created by
 * 项目名称：com.liux.easyreading.utils
 * 项目日期：2017/12/25
 * 作者：liux
 * 功能：
 *
 * @author 750954283(qq)
 */

public class SplashViewSettingUtil {

    /**
     * 有米广告
     */
    public static void inAdvert(Context context, ViewGroup viewGroup, SpotListener listener){
        AdManager.getInstance(context).init(SDKCommonKey.YOUMI_APPID, SDKCommonKey.YOUMI_APPKEY, false);
        SplashViewSettings splashViewSettings = new SplashViewSettings();
        splashViewSettings.setTargetClass(MainActivity.class);
        splashViewSettings.setSplashViewContainer(viewGroup);
        SpotManager.getInstance(context).showSplash(context,
                splashViewSettings, listener);
    }
}
