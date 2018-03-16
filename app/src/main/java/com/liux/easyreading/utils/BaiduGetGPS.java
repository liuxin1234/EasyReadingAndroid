package com.liux.easyreading.utils;

import android.content.Context;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.liux.easyreading.activity.moreFunction.BaiduMapActivity;

/**
 * Created by
 * 项目名称：com.liux.easyreading.utils
 * 项目日期：2017/11/29
 * 作者：liux
 * 功能：利用百度地图进行单次定位
 * @author 75095
 */

public class BaiduGetGPS {

    private Context mContext;

    // 定位相关
    LocationClient mLocClient;
    public MyLocationListenner myListener = new MyLocationListenner();

    public BaiduGetGPS(Context context){
        this.mContext = context;
        // 定位初始化,推荐使用getApplicationConext()方法获取全进程有效的Context。
        mLocClient = new LocationClient(context);
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        //可选，设置定位模式，默认高精度
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setOpenGps(true); // 打开gps
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setCoorType("bd09ll"); // 设置坐标类型
        //可选，设置发起定位请求的间隔，int类型，单位ms
        //如果设置为0，则代表单次定位，即仅定位一次，默认为0
        //如果设置非0，需设置1000ms以上才有效
        option.setScanSpan(0);
        mLocClient.setLocOption(option);

    }

    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null ) {
                return;
            }
            mGPSListener.onGpsChanged(location);
        }

        public void onReceivePoi(BDLocation poiLocation) {
        }
    }

    public interface GPSListener{
        void onGpsChanged(BDLocation location);
    }

    private GPSListener mGPSListener;

    public void setGPSListener(GPSListener gpsListener) {
        mGPSListener = gpsListener;
    }

    public void startGPSListener() {
        mLocClient.start();
    }

    public void stopGpsListener(){
        mLocClient.stop();
    }
}
