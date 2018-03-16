package com.liux.easyreading.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.liux.easyreading.R;
import com.liux.easyreading.common.SDKCommonKey;
import com.liux.easyreading.utils.PermissionHelper;
import com.liux.easyreading.utils.SplashViewSettingUtil;
import com.liux.easyreading.utils.ToastUtil;
import com.orhanobut.logger.Logger;

import net.youmi.android.AdManager;
import net.youmi.android.nm.cm.ErrorCode;
import net.youmi.android.nm.sp.SpotListener;
import net.youmi.android.nm.sp.SpotManager;
import net.youmi.android.nm.sp.SpotRequestListener;

/**
 * Created by
 * 项目名称：com.liux.easyreading.activity
 * 项目日期：2017/12/25
 * 作者：liux
 * 功能：
 *
 * @author 750954283(qq)
 */

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = "SplashActivity";
    private PermissionHelper mPermissionHelper;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 隐藏标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        initView();
    }


    public void initView() {
        //当系统为6.0以上时，需要申请权限
        mPermissionHelper = new PermissionHelper(this);
        mPermissionHelper.setOnApplyPermissionListener(new PermissionHelper.OnApplyPermissionListener() {
            @Override
            public void onAfterApplyAllPermission() {
                Log.i(TAG, "All of requested permissions has been granted, so run app logic.");
                runApp();
            }
        });
        if (Build.VERSION.SDK_INT < 23){
           // 如果系统版本低于23，直接跑应用的逻辑
            runApp();
        }else {
            // 如果权限全部申请了，那就直接跑应用逻辑
            if (mPermissionHelper.isAllRequestedPermissionGranted()){
                runApp();
            }else {
                mPermissionHelper.applyPermissions();
                if (mPermissionHelper.isAllRequestedPermissionGranted()){
                    runApp();
                }else {
                    // 如果还有权限为申请，而且系统版本大于23，执行申请权限逻辑
                    mPermissionHelper.applyPermissions();
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mPermissionHelper.onRequestPermissionsResult(requestCode,permissions,grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPermissionHelper.onActivityResult(requestCode,resultCode,data);
    }

    /**
     * 跑应用的逻辑
     */
    private void runApp() {
        //初始化SDK
        AdManager.getInstance(this).init(SDKCommonKey.YOUMI_APPID, SDKCommonKey.YOUMI_APPKEY,true);
        preloadAd();
        //setupSplashAd();// 如果需要首次展示开屏，请注释掉本句代码
    }

    private void setupSplashAd() {
        // 创建开屏容器
        final RelativeLayout splashLayout = (RelativeLayout) findViewById(R.id.rl_splash);
        //对开屏进行设置
        SplashViewSettingUtil.inAdvert(this, splashLayout, new SpotListener() {
            @Override
            public void onShowSuccess() {
               Logger.e("开屏展示成功");
            }

            @Override
            public void onShowFailed(int errorCode) {
                Logger.e("开屏展示失败");
                switch (errorCode){
                    case ErrorCode.NON_NETWORK:
                        Logger.e("网络异常");
                        break;
                    case ErrorCode.NON_AD:
                        Logger.e("暂无开屏广告");
                        break;
                    case ErrorCode.RESOURCE_NOT_READY:
                        Logger.e("开屏资源还没准备好");
                        break;
                    case ErrorCode.SHOW_INTERVAL_LIMITED:
                        Logger.e("开屏展示间隔限制");
                        break;
                    case ErrorCode.WIDGET_NOT_IN_VISIBILITY_STATE:
                        Logger.e("开屏控件处在不可见状态");
                        break;
                    default:
                        Logger.e("errorCode: %d", errorCode);
                        break;
                }
            }

            @Override
            public void onSpotClosed() {
                Logger.e("开屏被关闭");
            }

            @Override
            public void onSpotClicked(boolean b) {
                Logger.e("开屏被点击");

            }
        });

    }
    /**
     * 预加载广告
     */
    private void preloadAd() {
        //注意：不必每次展示插播广告前都请求，只需在应用启动时请求一次
        SpotManager.getInstance(this).requestSpot(new SpotRequestListener() {
            @Override
            public void onRequestSuccess() {
                Logger.e("请求插播广告成功");
                //				// 应用安装后首次展示开屏会因为本地没有数据而跳过
                //              // 如果开发者需要在首次也能展示开屏，可以在请求广告成功之前展示应用的logo，请求成功后再加载开屏
                				setupSplashAd();
            }

            @Override
            public void onRequestFailed(int errorCode) {
                Logger.e("请求插播广告失败，errorCode: %s", errorCode);
                switch (errorCode){
                    case ErrorCode.NON_NETWORK:
                        ToastUtil.showToast(SplashActivity.this,"网络异常");
                        break;
                    case ErrorCode.NON_AD:
                        ToastUtil.showToast(SplashActivity.this,"暂无视频广告");
                        break;
                    default:
                        ToastUtil.showToast(SplashActivity.this,"请稍后再试");
                        break;
                }
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 开屏展示界面的 onDestroy() 回调方法中调用
        SpotManager.getInstance(this).onDestroy();
    }
}
