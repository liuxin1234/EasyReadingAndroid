package com.liux.easyreading.activity.moreFunction;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.baidu.location.BDLocation;
import com.liux.easyreading.R;
import com.liux.easyreading.adapter.MoreFunctionAdapter;
import com.liux.easyreading.base.BaseActivity;
import com.liux.easyreading.base.BaseSwipeBackActivity;
import com.liux.easyreading.utils.BaiduGetGPS;
import com.liux.easyreading.utils.BaiduLocation;
import com.liux.easyreading.utils.FunctionDataUtils;
import com.liux.easyreading.utils.GsonUtils;
import com.liux.easyreading.view.ToolbarHelper;
import com.liux.easyreading.widget.NoScrollGridView;
import com.liux.module.moreFunctionBean.FunctionBean;
import com.liux.module.weatherBean.WeatherBean;
import com.mob.mobapi.API;
import com.mob.mobapi.APICallback;
import com.mob.mobapi.apis.Weather;
import com.orhanobut.logger.Logger;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import butterknife.BindView;



/**
 * Created by
 * 项目名称：com.liux.easyreading.activity.moreFunction
 * 项目日期：2017/11/6
 * 作者：liux
 * 功能：应用界面
 *
 * @author 75095
 */

public class FunctionActivity extends BaseSwipeBackActivity {

    private static final int BAIDU_ACCESS_COARSE_LOCATION = 1;
    @BindView(R.id.gv_project)
    NoScrollGridView gvProject;

    MoreFunctionAdapter mFunctionAdapter;

    private double longitude = 0.0; //纬度
    private double latitude = 0.0; //经度
    private  String mCity; //城市
    private  String mProvince; //省份

    @Override
    protected void initToolbar(ToolbarHelper toolbarHelper) {
        toolbarHelper.setTitle("应用功能");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_function;
    }

    @Override
    protected void initView() {
        myPermissionRequest();
        initAdapter();
    }




    private void initAdapter() {
        List<FunctionBean> beanList = FunctionDataUtils.getItemData();
        mFunctionAdapter = new MoreFunctionAdapter(this,beanList);
        gvProject.setNumColumns(4);
        gvProject.setAdapter(mFunctionAdapter);
        gvProject.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                switch (position){
                    case 0:
                        //历史上的今天
                        intent.setClass(FunctionActivity.this,HistroyTodayActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        //天气预报
                        getWeatherData(intent);
                        break;
                    case 2:
                        //百度地图
                        intent.setClass(FunctionActivity.this,BaiduMapActivity.class);
                        startActivity(intent);
                        break;
                    case 3:
                        //空气质量
                        intent.setClass(FunctionActivity.this,AirQualityActivity.class);
                        startActivity(intent);
                        break;
                    case 4:
                        //新华字典
                        intent.setClass(FunctionActivity.this,DictionaryAPIActivity.class);
                        startActivity(intent);
                        break;
                    case 5:
                        //汽车信息
                        intent.setClass(FunctionActivity.this,CarAPIActivity.class);
                        startActivity(intent);
                        break;
                    case 6:
                        //智能刷新
                        intent.setClass(FunctionActivity.this,SmartRefreshLayoutActivity.class);
                        startActivity(intent);
                        break;
                    case 7:
                        //美图福利
                        intent.setClass(FunctionActivity.this,FuLiActivity.class);
                        startActivity(intent);
                    default:
                        break;
                }
            }
        });
    }

    /**
     * 动态请求权限，安卓手机版本在6.0以上时需要
     */
    private void myPermissionRequest() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 检查是否拥有权限，申请一个（或多个）权限，并提供用于回调返回的获取码（用户定义)
            if (this.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[] {
                        Manifest.permission.ACCESS_COARSE_LOCATION
                }, BAIDU_ACCESS_COARSE_LOCATION);
            }
            else {
                // 已拥有权限，作相应处理（调用定位SDK应当确保相关权限均被授权，否则可能引起定位失败）
                myLocation();
            }
        }else {
            // 安卓手机版本低于在6.0时，配置清单中已申明权限，作相应处理，此处正对sdk版本低于23的手机
            myLocation();
        }
    }

    /**
     * 百度地图定位的请求方法
     */
    private void myLocation() {
        BaiduLocation.getLocation(this);
        BaiduLocation.setMyLocationListener(new BaiduLocation.MyLocationListener() {
            @Override
            public void myLocatin(final double mylongitude, final double mylatitude, final String province, final String city, final String street) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // 得到的定位数据进行处理
                        Log.e("=========", "mylongitude = "+mylongitude+"mylatitude = "+mylatitude+"province = "+province+"city = "+city+"street = "+street);
                        mCity = city;
                        mProvince = province;
                    }
                });
            }
        });
    }

    /**
     * 权限请求的返回结果
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            // requestCode即所声明的权限获取码，在checkSelfPermission时传入
            case BAIDU_ACCESS_COARSE_LOCATION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // 第一次获取到权限，请求定位
                    myLocation();
                    Logger.e("onRequestPermissionsResult");
                }
                else {
                    // 没有获取到权限，做特殊处理
                    Log.i("=========", "请求权限失败");
                }
                break;

            default:
                break;
        }
    }

    /**
     * 获取天气数据
     */
    private void getWeatherData(final Intent intent) {
        if (mCity == null){
            return;
        }
        Logger.e(mCity+"\n"+mProvince);
        String sCity = mCity .substring(0, mCity .length() - 1);
        Weather weather = new Weather();
        weather.queryByCityName(sCity,mProvince, new APICallback() {
            @Override
            public void onSuccess(API api, int i, Map<String, Object> map) {
                WeatherBean weatherBean = GsonUtils.object(map, WeatherBean.class);
                if (weatherBean != null) {
                    WeatherBean.ResultBean resultBean = weatherBean.getResult().get(0);
                    intent.setClass(FunctionActivity.this,WeatherActivity.class);
                    intent.putExtra("weatherBean", (Serializable) resultBean);
                    startActivity(intent);
                }
            }

            @Override
            public void onError(API api, int i, Throwable throwable) {
                Logger.e(throwable.toString());
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
