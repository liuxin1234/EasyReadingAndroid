package com.liux.easyreading.activity.moreFunction;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.liux.easyreading.R;
import com.liux.easyreading.adapter.CarSeriesDetailAdapter;
import com.liux.easyreading.base.BaseActivity;
import com.liux.easyreading.utils.GsonUtils;
import com.liux.easyreading.utils.ToastUtil;
import com.liux.easyreading.view.DividerItemDecoration;
import com.liux.easyreading.view.ToolbarHelper;
import com.liux.module.carBean.CarSeriesDetailBean;
import com.mob.mobapi.API;
import com.mob.mobapi.APICallback;
import com.mob.mobapi.MobAPI;
import com.mob.mobapi.apis.Car;
import com.mob.tools.utils.ResHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by
 * 项目名称：com.liux.easyreading.activity.moreFunction
 * 项目日期：2017/12/4
 * 作者：liux
 * 功能：
 *
 * @author 75095
 */

public class CarSeriesDetailActivity extends BaseActivity implements APICallback {


    @BindView(R.id.recycle_View)
    RecyclerView recycleView;
    @BindView(R.id.refresh_layout)
    MaterialRefreshLayout refreshLayout;

    private String cid;

    private CarSeriesDetailAdapter mCarSeriesDetailAdapter;
    private List<CarSeriesDetailBean.ResultBean> mBeanList = new ArrayList<>();
    private CarSeriesDetailBean.ResultBean resultBean;

    @Override
    protected void initToolbar(ToolbarHelper toolbarHelper) {
        toolbarHelper.setTitle("汽车信息详情");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_car_series_detail;
    }

    @Override
    protected void initView() {
        cid = getIntent().getStringExtra("cid");
        initData();
        initRefresh();
    }

    private void initData() {
        //查询车型详细信息 cid和name必须填一个，默认使用cid
        Car api = ResHelper.forceCast(MobAPI.getAPI(Car.NAME));
        api.querySeries(cid, this);
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
                }, 3000);
            }
        });
    }

    private void initAdapter(List<Map<String, Object>> list) {
        mCarSeriesDetailAdapter = new CarSeriesDetailAdapter(this, R.layout.item_layout_car_series_detail,list);
        mCarSeriesDetailAdapter.addHeaderView(getAddHeaderView(1),0);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        recycleView.addItemDecoration(new DividerItemDecoration(this, 1));
        recycleView.setAdapter(mCarSeriesDetailAdapter);
    }

    @SuppressLint("SetTextI18n")
    private View getAddHeaderView(int type) {
        ViewHolder viewHolder = null;
        View view = getLayoutInflater().inflate(R.layout.item_layout_car_series_detail_header, (ViewGroup) recycleView.getParent(), false);
        if (type == 1) {
            viewHolder = new ViewHolder(view);
            viewHolder.tvCarBrand.setText("品牌名称："+resultBean.getBrand());
            viewHolder.tvCarBrandName.setText("车系名称："+resultBean.getBrandName());
            viewHolder.tvCarSeriesName.setText("车型名称："+resultBean.getSeriesName());
            viewHolder.tvCarSonBrand.setText("汽车子品牌或合资品牌："+resultBean.getSonBrand());
            Glide.with(this)
                    .load(resultBean.getCarImage())
                    .into(viewHolder.imgCarImage);
        }

        return view;
    }

    @Override
    public void onSuccess(API api, int i, Map<String, Object> map) {
        CarSeriesDetailBean carSeriesDetailBean = GsonUtils.object(map, CarSeriesDetailBean.class);
        mBeanList.clear();
        mBeanList.addAll(carSeriesDetailBean.getResult());
        resultBean = mBeanList.get(0);
        getItemInfoData(map);

    }

    @Override
    public void onError(API api, int i, Throwable throwable) {
        ToastUtil.showToast(this, throwable.getMessage());
    }



    private void getItemInfoData(Map<String, Object> result){
        try {
            List<Map<String, Object>> resList = (List<Map<String, Object>>) result.get("result");
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            StringBuffer sb = new StringBuffer();
            if (resList != null && resList.size() > 0) {
                Map<String, Object> res = resList.get(0);
                if (res != null && !res.isEmpty()) {
                    Map<String, Object> item;
                    List<Map<String, Object>> info = (List<Map<String, Object>>) res.get("baseInfo");
                    if (info != null && !info.isEmpty()) {
                        item = new HashMap<String, Object>();
                        item.put("name", getString(R.string.car_api_item_base_info));
                        item.put("info", getInfo(info));
                        list.add(item);
                    }

                    info = (List<Map<String, Object>>) res.get("airConfig");
                    if (info != null && !info.isEmpty()) {
                        item = new HashMap<String, Object>();
                        item.put("name", getString(R.string.car_api_item_air_config));
                        item.put("info", getInfo(info));
                        list.add(item);
                    }

                    info = (List<Map<String, Object>>) res.get("carbody");
                    if (info != null && !info.isEmpty()) {
                        item = new HashMap<String, Object>();
                        item.put("name", getString(R.string.car_api_item_car_body));
                        item.put("info", getInfo(info));
                        list.add(item);
                    }

                    info = (List<Map<String, Object>>) res.get("chassis");
                    if (info != null && !info.isEmpty()) {
                        item = new HashMap<String, Object>();
                        item.put("name", getString(R.string.car_api_item_chassis));
                        item.put("info", getInfo(info));
                        list.add(item);
                    }

                    info = (List<Map<String, Object>>) res.get("controlConfig");
                    if (info != null && !info.isEmpty()) {
                        item = new HashMap<String, Object>();
                        item.put("name", getString(R.string.car_api_item_control_config));
                        item.put("info", getInfo(info));
                        list.add(item);
                    }

                    info = (List<Map<String, Object>>) res.get("engine");
                    if (info != null && !info.isEmpty()) {
                        item = new HashMap<String, Object>();
                        item.put("name", getString(R.string.car_api_item_engine));
                        item.put("info", getInfo(info));
                        list.add(item);
                    }

                    info = (List<Map<String, Object>>) res.get("exterConfig");
                    if (info != null && !info.isEmpty()) {
                        item = new HashMap<String, Object>();
                        item.put("name", getString(R.string.car_api_item_exter_config));
                        item.put("info", getInfo(info));
                        list.add(item);
                    }

                    info = (List<Map<String, Object>>) res.get("glassConfig");
                    if (info != null && !info.isEmpty()) {
                        item = new HashMap<String, Object>();
                        item.put("name", getString(R.string.car_api_item_glass_config));
                        item.put("info", getInfo(info));
                        list.add(item);
                    }

                    info = (List<Map<String, Object>>) res.get("interConfig");
                    if (info != null && !info.isEmpty()) {
                        item = new HashMap<String, Object>();
                        item.put("name", getString(R.string.car_api_item_inter_config));
                        item.put("info", getInfo(info));
                        list.add(item);
                    }

                    info = (List<Map<String, Object>>) res.get("lightConfig");
                    if (info != null && !info.isEmpty()) {
                        item = new HashMap<String, Object>();
                        item.put("name", getString(R.string.car_api_item_light_config));
                        item.put("info", getInfo(info));
                        list.add(item);
                    }

                    info = (List<Map<String, Object>>) res.get("mediaConfig");
                    if (info != null && !info.isEmpty()) {
                        item = new HashMap<String, Object>();
                        item.put("name", getString(R.string.car_api_item_media_config));
                        item.put("info", getInfo(info));
                        list.add(item);
                    }

                    info = (List<Map<String, Object>>) res.get("safetyDevice");
                    if (info != null && !info.isEmpty()) {
                        item = new HashMap<String, Object>();
                        item.put("name", getString(R.string.car_api_item_safety_device));
                        item.put("info", getInfo(info));
                        list.add(item);
                    }

                    info = (List<Map<String, Object>>) res.get("seatConfig");
                    if (info != null && !info.isEmpty()) {
                        item = new HashMap<String, Object>();
                        item.put("name", getString(R.string.car_api_item_seat_config));
                        item.put("info", getInfo(info));
                        list.add(item);
                    }

                    info = (List<Map<String, Object>>) res.get("techConfig");
                    if (info != null && !info.isEmpty()) {
                        item = new HashMap<String, Object>();
                        item.put("name", getString(R.string.car_api_item_tech_config));
                        item.put("info", getInfo(info));
                        list.add(item);
                    }

                    info = (List<Map<String, Object>>) res.get("transmission");
                    if (info != null && !info.isEmpty()) {
                        item = new HashMap<String, Object>();
                        item.put("name", getString(R.string.car_api_item_transmission));
                        item.put("info", getInfo(info));
                        list.add(item);
                    }

                    info = (List<Map<String, Object>>) res.get("wheelInfo");
                    if (info != null && !info.isEmpty()) {
                        item = new HashMap<String, Object>();
                        item.put("name", getString(R.string.car_api_item_wheel_info));
                        item.put("info", getInfo(info));
                        list.add(item);
                    }

                    info = (List<Map<String, Object>>) res.get("motorList");
                    if (info != null && !info.isEmpty()) {
                        item = new HashMap<String, Object>();
                        item.put("name", getString(R.string.car_api_item_motor_list));
                        item.put("info", getInfo(info));
                        list.add(item);
                    }
                }

                initAdapter(list);
            }
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    @NonNull
    private String getInfo(List<Map<String, Object>> info) {
        StringBuffer sb = new StringBuffer();
        for (Map<String, Object> tmp : info) {
            sb.append(tmp.get("name"));
            sb.append(": ");
            sb.append(tmp.get("value"));
            sb.append("\n");
        }
        return sb.toString();
    }


    static class ViewHolder {
        @BindView(R.id.img_carImage)
        ImageView imgCarImage;
        @BindView(R.id.tv_car_brand)
        TextView tvCarBrand;
        @BindView(R.id.tv_car_brandName)
        TextView tvCarBrandName;
        @BindView(R.id.tv_car_seriesName)
        TextView tvCarSeriesName;
        @BindView(R.id.tv_car_sonBrand)
        TextView tvCarSonBrand;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
