package com.liux.easyreading.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.liux.easyreading.R;
import com.liux.easyreading.widget.CustomVideoView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by
 * 项目名称：com.liux.easyreading.activity
 * 项目日期：2017/11/2
 * 作者：liux
 * 功能：
 */

public class WelcomeActivity extends AppCompatActivity {

    @BindView(R.id.welcome_videoview)
    CustomVideoView welcomeVideoview;
    @BindView(R.id.welcome_button)
    Button welcomeButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 隐藏标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        welcomeVideoview.setVideoURI(Uri.parse("android.resource://"+this.getPackageName()+"/"+R.raw.kr36));
        welcomeVideoview.start();
        welcomeVideoview.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                welcomeVideoview.start();
            }
        });

    }

    @OnClick(R.id.welcome_button)
    public void onViewClicked() {
        if (welcomeVideoview.isPlaying()){
            welcomeVideoview.stopPlayback();
            welcomeVideoview = null;
        }
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }



}
