package com.liux.easyreading.activity.moreFunction;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.liux.easyreading.R;
import com.liux.easyreading.adapter.CarApiAdapter;
import com.liux.easyreading.base.BaseActivity;
import com.liux.easyreading.utils.GsonUtils;
import com.liux.easyreading.utils.ToastUtil;
import com.liux.easyreading.view.DividerItemDecoration;
import com.liux.easyreading.view.ToolbarHelper;
import com.liux.module.carBean.CarBean;
import com.mob.mobapi.API;
import com.mob.mobapi.APICallback;
import com.mob.mobapi.MobAPI;
import com.mob.mobapi.apis.Car;
import com.mob.tools.utils.ResHelper;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by
 * 项目名称：com.liux.easyreading.activity.moreFunction
 * 项目日期：2017/12/1
 * 作者：liux
 * 功能：汽车查询界面
 *
 * @author 75095
 */

public class CarAPIActivity extends BaseActivity implements APICallback {

    @BindView(R.id.recycle_View)
    RecyclerView recycleView;
    @BindView(R.id.refresh_layout)
    MaterialRefreshLayout refreshLayout;

    private CarApiAdapter mCarApiAdapter;

    private List<CarBean.ResultBean> mBeanList = new ArrayList<CarBean.ResultBean>();

    @Override
    protected void initToolbar(ToolbarHelper toolbarHelper) {
        toolbarHelper.setTitle("汽车信息查询");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_car;
    }

    @Override
    protected void initView() {
        initData();
        initRefresh();
        initAdapter();
    }

    private void initData() {
        //查询所有汽车品牌
        Car api = ResHelper.forceCast(MobAPI.getAPI(Car.NAME));
        api.queryBrand(this);
    }

    private void initAdapter() {
        mCarApiAdapter = new CarApiAdapter(this,R.layout.item_layout_car,mBeanList);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        recycleView.addItemDecoration(new DividerItemDecoration(this,1));
        recycleView.setAdapter(mCarApiAdapter);
        mCarApiAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ToastUtil.showToast(CarAPIActivity.this,""+position);
            }
        });
    }

    private void initRefresh() {
        refreshLayout.setLoadMore(false);
        refreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                materialRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        initData();
                        refreshLayout.finishRefresh();
                    }
                },3000);
            }
        });
    }

    @Override
    public void onSuccess(API api, int i, Map<String, Object> map) {
        CarBean carBean = GsonUtils.object(map, CarBean.class);
        mBeanList.clear();
        mBeanList.addAll(carBean.getResult());
        mCarApiAdapter.notifyDataSetChanged();
    }

    @Override
    public void onError(API api, int action, Throwable throwable) {
        Logger.e(throwable.getMessage());
    }
}
