package com.liux.easyreading.activity.moreFunction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.liux.easyreading.R;
import com.liux.easyreading.base.BaseActivity;
import com.liux.easyreading.view.ToolbarHelper;
import com.mob.mobapi.API;
import com.mob.mobapi.APICallback;
import com.mob.mobapi.MobAPI;
import com.mob.mobapi.apis.Environment;
import com.mob.tools.utils.ResHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by
 * 项目名称：com.liux.easyreading.activity.moreFunction
 * 项目日期：2017/12/1
 * 作者：liux
 * 功能：空气质量API
 */

public class AirQualityActivity extends BaseActivity implements APICallback {


    @BindView(R.id.etCity)
    EditText etCity;
    @BindView(R.id.btnSearch)
    Button btnSearch;
    @BindView(R.id.tvProvince)
    TextView tvProvince;
    @BindView(R.id.tvCity)
    TextView tvCity;
    @BindView(R.id.tvDistrict)
    TextView tvDistrict;
    @BindView(R.id.tvAqi)
    TextView tvAqi;
    @BindView(R.id.tvNo2)
    TextView tvNo2;
    @BindView(R.id.tvPm10)
    TextView tvPm10;
    @BindView(R.id.tvPm25)
    TextView tvPm25;
    @BindView(R.id.tvQuality)
    TextView tvQuality;
    @BindView(R.id.tvSo2)
    TextView tvSo2;
    @BindView(R.id.llHourData)
    LinearLayout llHourData;
    @BindView(R.id.llFetureData)
    LinearLayout llFetureData;
    @BindView(R.id.tvUpdateTime)
    TextView tvUpdateTime;
    @BindView(R.id.llResult)
    LinearLayout llResult;

    @Override
    protected void initToolbar(ToolbarHelper toolbarHelper) {
        toolbarHelper.setTitle("空气质量");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_air_quality;
    }

    @Override
    protected void initView() {

    }

    @OnClick(R.id.btnSearch)
    public void onViewClicked() {
        // 获取API实例，请求空气质量信息
        Environment api = ResHelper.forceCast(MobAPI.getAPI(Environment.NAME));
        api.query(etCity.getText().toString().trim(), null, this);
    }

    @Override
    public void onSuccess(API api, int i, Map<String, Object> result) {
        @SuppressWarnings("unchecked")
        ArrayList<HashMap<String, Object>> list = (ArrayList<HashMap<String, Object>>) result.get("result");
        HashMap<String, Object> data = list.get(0);
        llHourData.setVisibility(View.GONE);
        ((LinearLayout) llHourData.getChildAt(2)).removeAllViews();
        llFetureData.setVisibility(View.GONE);
        ((LinearLayout) llFetureData.getChildAt(2)).removeAllViews();
        updateUI(data);
    }

    @Override
    public void onError(API api, int action, Throwable details) {
        details.printStackTrace();
        Toast.makeText(this, R.string.error_raise, Toast.LENGTH_SHORT).show();
    }


    private void updateUI(HashMap<String, Object> result) {
        tvProvince.setText(ResHelper.toString(result.get("province")));
        tvCity.setText(ResHelper.toString(result.get("city")));
        tvDistrict.setText(ResHelper.toString(result.get("district")));
        tvAqi.setText(ResHelper.toString(result.get("aqi")));
        tvNo2.setText(ResHelper.toString(result.get("no2")));
        tvPm10.setText(ResHelper.toString(result.get("pm10")));
        tvPm25.setText(ResHelper.toString(result.get("pm25")));
        tvQuality.setText(ResHelper.toString(result.get("quality")));
        tvSo2.setText(ResHelper.toString(result.get("so2")));
        tvUpdateTime.setText(ResHelper.toString(result.get("updateTime")));

        @SuppressWarnings("unchecked")
        ArrayList<HashMap<String, Object>> hourData = (ArrayList<HashMap<String,Object>>) result.get("hourData");
        if (hourData != null && hourData.size() > 0) {
            llHourData.setVisibility(View.VISIBLE);
            LinearLayout llList = (LinearLayout) llHourData.getChildAt(2);
            for (HashMap<String, Object> hour : hourData) {
                View llHour = View.inflate(this, R.layout.view_environment_hour, null);
                TextView tvTime = (TextView) llHour.findViewById(R.id.tvTime);
                String dateTime = ResHelper.toString(hour.get("dateTime"));
                tvTime.setText(dateTime.split(" ")[1]);
                TextView tvAqi = (TextView) llHour.findViewById(R.id.tvAqi);
                tvAqi.setText(ResHelper.toString(hour.get("aqi")));
                llList.addView(llHour);
            }
        }

        @SuppressWarnings("unchecked")
        ArrayList<HashMap<String, Object>> fetureData = (ArrayList<HashMap<String,Object>>) result.get("fetureData");
        if (fetureData != null && fetureData.size() > 0) {
            llFetureData.setVisibility(View.VISIBLE);
            LinearLayout llList = (LinearLayout) llFetureData.getChildAt(2);
            for (HashMap<String, Object> data : fetureData) {
                View llDate = View.inflate(this, R.layout.view_environment_hour, null);
                TextView tvTime = (TextView) llDate.findViewById(R.id.tvTime);
                tvTime.setText(ResHelper.toString(data.get("date")));
                TextView tvAqi = (TextView) llDate.findViewById(R.id.tvAqi);
                String aqi = ResHelper.toString(data.get("aqi"));
                String quality = ResHelper.toString(data.get("quality"));
                tvAqi.setText(aqi + " (" + quality + ")");
                llList.addView(llDate);
            }
        }
    }
}
