package com.liux.easyreading.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by
 * 项目名称：com.example.administrator.mobileoa_android.common.utils
 * 项目日期：2017/9/28
 * 作者：liux
 * 功能：
 */

public class ToastUtil {

    public static void showToast(Context context,String msg){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }

}
