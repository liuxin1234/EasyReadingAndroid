package com.liux.easyreading.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by
 * 项目名称：com.liux.easyreading.base
 * 项目日期：2017/11/2
 * 作者：liux
 * 功能：
 */

public abstract class BaseFragment extends Fragment {

    private Unbinder mUnbinder;
    private ProgressDialog mDialog;
    private View mView;
    private boolean isViewCreated; //Fragment的View加载完毕的标记
    private boolean isUIVisible;    //Fragment对用户可见的标记



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(setLayout(),container,false);
        mUnbinder = ButterKnife.bind(this,mView);
        mDialog = new ProgressDialog(getActivity());

        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isViewCreated = true;
        lazyLoad();
    }

    /**
     * fragment懒加载
     */
    private void lazyLoad() {
        /**
         * 这里进行双重标记判断,是因为setUserVisibleHint会多次回调,并且会在onCreateView执行前回调,
         * 必须确保onCreateView加载完毕且页面可见,才加载数据
         */
        if (isViewCreated && isUIVisible) {
            loadData();
            //数据加载完毕,恢复标记,防止重复加载
            isViewCreated = false;
            isUIVisible = false;
        }
    }


    public abstract int setLayout();


    /**
     * 当用户可见当时候加载数据
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser){
            isUIVisible = true;
            lazyLoad();
        }else {
            isUIVisible = false;
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //View的初始化view
        initView();
        if (getUserVisibleHint()){
            loadData();
        }
    }

    /**
     * 初始化view
     */
    public abstract void initView();

    /**
     * 加载数据
     */
    protected abstract void loadData();

    /**
     * 显示提示框
     * @param msg：提示信息
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
        try {
            if (mDialog !=null && mDialog.isShowing()){
                mDialog.dismiss();
            }
        }catch (Exception ignored){

        }
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mUnbinder != Unbinder.EMPTY){
            mUnbinder.unbind();
        }
        //页面销毁,恢复标记
        isViewCreated = false;
        isUIVisible = false;
    }
}
