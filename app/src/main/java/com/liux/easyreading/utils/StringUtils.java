package com.liux.easyreading.utils;

/**
 * Created by
 * 项目名称：com.liux.easyreading.utils
 * 项目日期：2017/11/6
 * 作者：liux
 * 功能：
 * @author 75095
 */

public class StringUtils {
    public StringUtils() {
    }

    public static boolean isBlank(String str) {
        int strLen;
        if(str != null && (strLen = str.length()) != 0) {
            for(int i = 0; i < strLen; ++i) {
                if(!Character.isWhitespace(str.charAt(i))) {
                    return false;
                }
            }

            return true;
        } else {
            return true;
        }
    }
}
