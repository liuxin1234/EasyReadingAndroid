package com.liux.easyreading.activity;

import android.content.Intent;
import android.widget.Button;
import android.widget.Toast;

import com.liux.easyreading.R;
import com.liux.easyreading.base.BaseActivity;
import com.liux.easyreading.base.BaseSwipeBackActivity;
import com.liux.easyreading.utils.EncryptUtil;
import com.liux.easyreading.utils.GsonUtils;
import com.liux.easyreading.view.ToolbarHelper;
import com.liux.easyreading.widget.ClearEditText;
import com.liux.module.userBean.userRegisterBean;
import com.mob.mobapi.API;
import com.mob.mobapi.APICallback;
import com.mob.mobapi.apis.UserCenter;
import com.orhanobut.logger.Logger;

import java.util.Map;

import butterknife.BindView;
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

public class RegisterActivity extends BaseSwipeBackActivity {

    @BindView(R.id.etxt_userName)
    ClearEditText etxtUserName;
    @BindView(R.id.etxt_pwd)
    ClearEditText etxtPwd;
    @BindView(R.id.etxt_email)
    ClearEditText etxtEmail;
    @BindView(R.id.btn_register)
    Button btnRegister;

    private String userName;
    private String passWord;
    private String email;

    @Override
    protected void initToolbar(ToolbarHelper toolbarHelper) {
        toolbarHelper.setTitle("注册");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView() {

    }

    private void initGetText() {

        userName = etxtUserName.getText().toString().trim();
        passWord = etxtPwd.getText().toString().trim();
        email = etxtEmail.getText().toString().trim();

        if (userName.isEmpty() || passWord.isEmpty() || email.isEmpty()){
            Toast.makeText(this, "请填写完整资料", Toast.LENGTH_SHORT).show();
        }else {
            initRegisterData();
        }

    }

    private void initRegisterData() {
        UserCenter userCenter = new UserCenter();
        userCenter.register(userName, EncryptUtil.makeMD5(passWord), email, new APICallback() {
            @Override
            public void onSuccess(API api, int i, Map<String, Object> map) {
                userRegisterBean registerBean = GsonUtils.object(map, userRegisterBean.class);
                if ("success".equals(registerBean.getMsg())){
                    String uid = registerBean.getResult();
                    Logger.e(uid);
                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onError(API api, int i, Throwable throwable) {
                Toast.makeText(RegisterActivity.this, "注册失败，"+throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.btn_register)
    public void onViewClicked() {
        initGetText();
    }
}
