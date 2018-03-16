package com.liux.easyreading.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.liux.easyreading.R;
import com.liux.easyreading.activity.MainActivity;
import com.liux.easyreading.adapter.FixedPagerAdapter;
import com.liux.easyreading.base.BaseFragment;
import com.liux.module.wxBean.CategoriesBean;
import com.mingle.widget.LoadingView;
import com.mob.mobapi.API;
import com.mob.mobapi.APICallback;
import com.mob.mobapi.MobAPI;
import com.mob.mobapi.apis.WxArticle;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by
 * 项目名称：com.liux.easyreading.fragment
 * 项目日期：2017/11/2
 * 作者：liux
 * 功能：
 *
 * @author 75095
 */

public class MainInfoFragment extends BaseFragment implements ViewPager.OnPageChangeListener {

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.info_viewpager)
    ViewPager infoViewpager;
    @BindView(R.id.loadView)
    LoadingView loadView;


    private List<Fragment> mFragments;
    private FixedPagerAdapter mPagerAdapter;
    private List<CategoriesBean.ResultBean> mResultBeanList = new ArrayList<>();

    @Override
    public int setLayout() {
        return R.layout.fragment_main_info;
    }

    @Override
    public void initView() {
        loadView.setVisibility(View.VISIBLE);
        initListener();
    }

    @Override
    protected void loadData() {
        initData();
    }

    private void initListener() {
        infoViewpager.addOnPageChangeListener(this);
    }

    private void initAdapter() {
        mPagerAdapter = new FixedPagerAdapter(getChildFragmentManager());
        mPagerAdapter.setCategoriesBeen(mResultBeanList);
        mFragments = new ArrayList<Fragment>();
        for (int i = 0; i < mResultBeanList.size(); i++) {
            BaseFragment fragment = null;
            if (i == 0) {
                //当前为第一个fragment
                fragment = HomeFragment.newInstance(mResultBeanList.get(i));
            } else {
                fragment = PageFragment.newInstance(mResultBeanList.get(i));
            }
            mFragments.add(fragment);
        }
        mPagerAdapter.setFragments(mFragments);
        infoViewpager.setAdapter(mPagerAdapter);
        tabLayout.setupWithViewPager(infoViewpager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        loadView.setVisibility(View.GONE);
    }

    private void initData() {
        WxArticle wxArticle = (WxArticle) MobAPI.getAPI(WxArticle.NAME);
        wxArticle.queryCategory(new APICallback() {
            @Override
            public void onSuccess(API api, int i, Map<String, Object> map) {
                Gson gson = new Gson();
                String json = gson.toJson(map);
                Logger.json(json);
                CategoriesBean categoriesBean = gson.fromJson(map.toString(), CategoriesBean.class);
                List<CategoriesBean.ResultBean> result = categoriesBean.getResult();
                mResultBeanList.addAll(result);
                initAdapter();
            }

            @Override
            public void onError(API api, int i, Throwable throwable) {

            }
        });
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position == 0) {
            //当viewPage滑动到第一个页面时，侧滑菜单才能进行侧滑出来，这样就不会与viewPage的滑动起冲突
            ((MainActivity) getActivity()).getDragLayout().setIsDrag(true);
        } else {
            ((MainActivity) getActivity()).getDragLayout().setIsDrag(false);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

}
