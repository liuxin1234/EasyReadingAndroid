package com.liux.easyreading.base;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.liux.easyreading.R;
import com.liux.easyreading.broadcastReceiver.NetBroadcastReceiver;
import com.liux.easyreading.utils.NetUtil;
import com.liux.easyreading.utils.ToastUtil;
import com.liux.easyreading.view.ToolbarHelper;

import java.lang.reflect.Field;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by
 * 项目名称：com.liux.easyreading.base
 * 项目日期：2017/11/2
 * 作者：liux
 * 功能：
 * @author 75095
 */

public abstract class BaseActivity extends AppCompatActivity implements NetBroadcastReceiver.NetEvevt,
        EasyPermissions.PermissionCallbacks {
    private static final String TAG = "BaseActivity";
    private Unbinder mUnbinder;
    private ProgressDialog mDialog;

    public static NetBroadcastReceiver.NetEvevt evevt;
    /**
     * 网络类型
     */
    private int netMobile;

    private boolean isOpenNet = false; //判断是否开启网络检测

    private static final String[] LOCATION_AND_CONTACTS =
            {Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private static final int RC_LOCATION_CONTACTS_PERM = 124;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //当系统版本为4.4或者4.4以上时可以使用沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

        setContentView(getContentView());
        mUnbinder = ButterKnife.bind(this);
        mDialog = new ProgressDialog(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null){
            setSupportActionBar(toolbar);
            //默认不显示原生标题
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            initToolbar(new ToolbarHelper(toolbar));
        }
        evevt = this;
        initView();
        locationAndContactsTask();

    }



    @Override
    protected void onResume() {
        super.onResume();
        if (isOpenNet) {
            inspectNet();
        }
    }

    /**
     * 用于判断是否开启网络检测
     */
    public void isOpenTestNet(boolean b){
        isOpenNet= b;
    }


    /**
     * 初始化时判断有没有网络
     */
    public boolean inspectNet() {
        this.netMobile = NetUtil.getNetWorkState(BaseActivity.this);
        return isNetConnect();
    }

    /**
     * 网络变化之后的类型 该方法接口 可以在各个activity中复写
     */
    @Override
    public void onNetChange(int netMobile) {
        // TODO Auto-generated method stub
        this.netMobile = netMobile;
        isNetConnect();
    }

    /**
     * 判断有无网络 。
     * //netMobile: -1,0,1 风别代表无无网络状态，移动网络，wifi网络
     * @return true 有网, false 没有网络.
     */
    public boolean isNetConnect() {
        if (netMobile == 1) {
            ToastUtil.showToast(this,"当前使用的wifi网络");
            return true;
        } else if (netMobile == 0) {
            ToastUtil.showToast(this,"当前使用的移动网络");
            return true;
        } else if (netMobile == -1) {
            dissMissNormalDialog();
            ToastUtil.showToast(this,"网络未连接，请先连接网络！！！");
            return false;
        }
        return false;
    }

    protected abstract void initToolbar(ToolbarHelper toolbarHelper);

    protected abstract int getContentView();

    /**
     * 初始化View
     */
    protected abstract void initView();


    /**
     * 显示提示框
     * @param msg：文字提示
     */
    public void showNormalDialog(String msg){
        mDialog.setMessage(msg);
        mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mDialog.show();
    }

    /**
     * 提示框消失
     */
    public void dissMissNormalDialog(){
        try{
            if (mDialog.isShowing() && mDialog !=null){
                mDialog.dismiss();
            }
        }catch (Exception ignored){

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != Unbinder.EMPTY){
            mUnbinder.unbind();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 设置沉浸式状态栏
     */
    protected void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            final ViewGroup linear_bar = (ViewGroup) findViewById(R.id.bar_layout);
            final int statusHeight = getStatusBarHeight();
            linear_bar.post(new Runnable() {
                @Override
                public void run() {
                    int titleHeight = linear_bar.getHeight();
                    android.widget.LinearLayout.LayoutParams params = (android.widget.LinearLayout.LayoutParams) linear_bar.getLayoutParams();
                    params.height = statusHeight + titleHeight;
                    linear_bar.setLayoutParams(params);
                }
            });
        }
    }
    /**
     * 获取状态栏的高度
     * @return
     */
    protected int getStatusBarHeight(){
        try
        {
            Class<?> c=Class.forName("com.android.internal.R$dimen");
            Object obj=c.newInstance();
            Field field=c.getField("status_bar_height");
            int x=Integer.parseInt(field.get(obj).toString());
            return  getResources().getDimensionPixelSize(x);
        }catch(Exception e){
            e.printStackTrace();
        }
        return 0;
    }




    @AfterPermissionGranted(RC_LOCATION_CONTACTS_PERM)
    private void locationAndContactsTask() {
        if (hasLocationAndContactsPermissions()){
            // 已经申请过权限，做想做的事
        }else {
            // 没有申请过权限，现在去申请
            EasyPermissions.requestPermissions(
                    this,
                    getString(R.string.rationale_location_contacts),
                    RC_LOCATION_CONTACTS_PERM,
                    LOCATION_AND_CONTACTS
            );
        }
    }




    //判断是否申请过定位，读写权限
    private boolean hasLocationAndContactsPermissions() {
        return EasyPermissions.hasPermissions(this, LOCATION_AND_CONTACTS);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // 把执行结果的操作给EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override //申请成功时调用
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        Log.d(TAG, "onPermissionsGranted:" + requestCode + ":" + perms.size());
    }

    @Override //申请失败时调用
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        Log.d(TAG, "onPermissionsDenied:" + requestCode + ":" + perms.size());

        // (Optional) Check whether the user denied any permissions and checked "NEVER ASK AGAIN."
        // This will display a dialog directing them to enable the permission in app settings.
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

}
