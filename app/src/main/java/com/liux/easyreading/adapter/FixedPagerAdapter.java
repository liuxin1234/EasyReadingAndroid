package com.liux.easyreading.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.liux.module.wxBean.CategoriesBean;

import java.util.List;

/**
 * Created by
 * 项目名称：com.liux.easyreading.adapter
 * 项目日期：2017/11/2
 * 作者：liux
 * 功能：
 */

public class FixedPagerAdapter extends FragmentStatePagerAdapter {

    private List<CategoriesBean.ResultBean> mResultBeanList;
    private List<Fragment> mFragments;

    public FixedPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setCategoriesBeen(List<CategoriesBean.ResultBean> mResultBeanList){
        this.mResultBeanList = mResultBeanList;
    }

    public void setFragments(List<Fragment> fragments){
        this.mFragments = fragments;
    }


    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = null;
        try {
            fragment = (Fragment) super.instantiateItem(container,position);
        }catch (Exception e){

        }
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mResultBeanList.get(position).getName();
    }
}
