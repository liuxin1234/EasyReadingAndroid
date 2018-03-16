package com.liux.easyreading.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.liux.easyreading.R;
import com.liux.easyreading.base.BaseActivity;
import com.liux.easyreading.base.BaseSwipeBackActivity;
import com.liux.easyreading.common.db.ACache;
import com.liux.easyreading.eventBus.LoginEvent;
import com.liux.easyreading.utils.EncryptUtil;
import com.liux.easyreading.utils.GsonUtils;
import com.liux.easyreading.view.ToolbarHelper;
import com.liux.easyreading.widget.ClearEditText;
import com.liux.module.userBean.userLoginBean;
import com.liux.module.userBean.userQueryDataBean;
import com.mob.mobapi.API;
import com.mob.mobapi.APICallback;
import com.mob.mobapi.apis.UserCenter;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by
 * 项目名称：com.liux.easyreading.activity
 * 项目日期：2017/11/7
 * 作者：liux
 * 功能：
 *
 * @author 75095
 */

public class LoginActivity extends BaseSwipeBackActivity {


    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.txt_toReg)
    TextView txtToReg;
    @BindView(R.id.tv_forget_password)
    TextView tvForgetPassword;
    @BindView(R.id.etxt_userName)
    ClearEditText etxtUserName;
    @BindView(R.id.etxt_pwd)
    ClearEditText etxtPwd;

    private String userName;
    private String passWord;

    private ACache mACache;

    @Override
    protected void initToolbar(ToolbarHelper toolbarHelper) {
        toolbarHelper.setTitle("登录");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        mACache = ACache.get(this);
        initSetText();
    }

    private void initSetText() {
        String userName = mACache.getAsString("userName");
        if (userName != null){
            etxtUserName.setText(userName);
            String passWord = mACache.getAsString("passWord");
            if (passWord != null){
                etxtPwd.setText(passWord);
            }
        }
    }

    private void initGetText() {
        userName = etxtUserName.getText().toString().trim();
        passWord = etxtPwd.getText().toString().trim();
        if (userName.isEmpty() || passWord.isEmpty()){
            Toast.makeText(this, "请输入完整信息", Toast.LENGTH_SHORT).show();
        }else {
            initLogin();
        }
    }

    private void initLogin() {
        final UserCenter userCenter = new UserCenter();
        userCenter.login(userName, EncryptUtil.makeMD5(passWord), new APICallback() {
            @Override
            public void onSuccess(API api, int i, Map<String, Object> map) {
                userLoginBean loginBean = GsonUtils.object(map, userLoginBean.class);
                if ("success".equals(loginBean.getMsg())){
                    String token = loginBean.getResult().getToken();
                    String uid = loginBean.getResult().getUid();
                    Logger.e(token+"\n"+uid);
                    mACache.put("userName",userName);
                    mACache.put("passWord",passWord);
                    mACache.put("token",token);
                    mACache.put("uid",uid);
                    EventBus.getDefault().post(new LoginEvent(true,userName));
                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onError(API api, int i, Throwable throwable) {
                Toast.makeText(LoginActivity.this, "登录失败，"+throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }




    @OnClick({R.id.btn_login, R.id.txt_toReg, R.id.tv_forget_password})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.btn_login:
                initGetText();
                break;
            case R.id.txt_toReg:
                intent.setClass(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_forget_password:
                break;
            default:
                break;
        }
    }


}
