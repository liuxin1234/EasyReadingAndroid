package com.liux.easyreading.activity.moreFunction;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.liux.easyreading.R;
import com.liux.easyreading.adapter.WeatherAdapter;
import com.liux.easyreading.base.BaseActivity;
import com.liux.easyreading.base.BaseSwipeBackActivity;
import com.liux.easyreading.utils.GsonUtils;
import com.liux.easyreading.view.ToolbarHelper;
import com.liux.module.weatherBean.WeatherBean;
import com.mob.mobapi.API;
import com.mob.mobapi.APICallback;
import com.mob.mobapi.apis.Weather;
import com.orhanobut.logger.Logger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by
 * 项目名称：com.liux.easyreading.activity.moreFunction
 * 项目日期：2017/11/8
 * 作者：liux
 * 功能：天气预报
 *
 * @author 75095
 */

public class WeatherActivity extends BaseSwipeBackActivity {


    @BindView(R.id.weatherRecyclerView)
    RecyclerView weatherRecyclerView;
    @BindView(R.id.tv_NowDate)
    TextView tvNowDate;
    @BindView(R.id.tv_City)
    TextView tvCity;
    @BindView(R.id.tv_Weather)
    TextView tvWeather;
    @BindView(R.id.tv_Tmp)
    TextView tvTmp;
    @BindView(R.id.tv_sug)
    TextView tvSug;
    @BindView(R.id.tv_Sunrise)
    TextView tvSunrise;
    @BindView(R.id.tv_Sunset)
    TextView tvSunset;
    @BindView(R.id.tv_Wind)
    TextView tvWind;
    @BindView(R.id.tv_Humidity)
    TextView tvHumidity;
    @BindView(R.id.tv_AirCondition)
    TextView tvAirCondition;
    @BindView(R.id.tv_dressingIndex)
    TextView tvDressingIndex;
    @BindView(R.id.tv_ColdIndex)
    TextView tvColdIndex;
    @BindView(R.id.tv_pollutionIndex)
    TextView tvPollutionIndex;
    @BindView(R.id.tv_WashIndex)
    TextView tvWashIndex;
    @BindView(R.id.tv_ExerciseIndex)
    TextView tvExerciseIndex;
    @BindView(R.id.img_back)
    ImageView imgBack;


    private WeatherAdapter mWeatherAdapter;

    private List<WeatherBean.ResultBean.FutureBean> mFutureBeanList = new ArrayList<>();

    private WeatherBean.ResultBean resultBean;

    @Override
    protected void initToolbar(ToolbarHelper toolbarHelper) {

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_weather;
    }

    @Override
    protected void initView() {
        Serializable weatherBean = getIntent().getSerializableExtra("weatherBean");
        if (weatherBean != null){
            resultBean = (WeatherBean.ResultBean) weatherBean;
            mFutureBeanList = resultBean.getFuture();
        }
        initSetText();
//        initData();
        initAdapter();
    }

    private void initSetText() {
        String city = resultBean.getCity(); //城市
        String weather = resultBean.getWeather();   //天气
        String temperature = resultBean.getTemperature();   //当前温度
        String sunrise = resultBean.getSunrise();   //日出时间
        String sunset = resultBean.getSunset(); //日落时间
        String wind = resultBean.getWind(); //风向
        String airCondition = resultBean.getAirCondition(); //轻度污染
        String dressingIndex = resultBean.getDressingIndex(); //单衣类
        String coldIndex = resultBean.getColdIndex(); //感冒指数
        String humidity = resultBean.getHumidity(); //湿度
        String pollutionIndex = resultBean.getPollutionIndex(); //空气质量指数
        String washIndex = resultBean.getWashIndex();   //洗车指数
        String exerciseIndex = resultBean.getExerciseIndex(); //运动指数

        tvCity.setText(city);
        tvWeather.setText(weather);
        tvTmp.setText(temperature);
        tvSunrise.setText(sunrise);
        tvSunset.setText(sunset);
        tvWind.setText(wind);
        tvAirCondition.setText(airCondition);
        tvDressingIndex.setText(dressingIndex);
        tvColdIndex.setText(coldIndex);
        String[] humiditySplit = humidity.split("：");
        tvHumidity.setText(humiditySplit[1]);
        tvPollutionIndex.setText(pollutionIndex);
        tvWashIndex.setText(washIndex);
        tvExerciseIndex.setText(exerciseIndex);
    }

    private void initAdapter() {
        mWeatherAdapter = new WeatherAdapter(R.layout.item_layout_weather, mFutureBeanList);
        weatherRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        weatherRecyclerView.setAdapter(mWeatherAdapter);
    }

    private void initData() {
        Weather weather = new Weather();
        weather.queryByCityName("宁波", "浙江", new APICallback() {
            @Override
            public void onSuccess(API api, int i, Map<String, Object> map) {
                WeatherBean weatherBean = GsonUtils.object(map, WeatherBean.class);
                if (weatherBean != null) {
                    WeatherBean.ResultBean resultBean = weatherBean.getResult().get(0);
                    String city = resultBean.getCity(); //城市
                    String weather = resultBean.getWeather();   //天气
                    String temperature = resultBean.getTemperature();   //当前温度
                    String sunrise = resultBean.getSunrise();   //日出时间
                    String sunset = resultBean.getSunset(); //日落时间
                    String wind = resultBean.getWind(); //风向
                    String airCondition = resultBean.getAirCondition(); //轻度污染
                    String dressingIndex = resultBean.getDressingIndex(); //单衣类
                    String coldIndex = resultBean.getColdIndex(); //感冒指数
                    String humidity = resultBean.getHumidity(); //湿度
                    String pollutionIndex = resultBean.getPollutionIndex(); //空气质量指数
                    String washIndex = resultBean.getWashIndex();   //洗车指数
                    String exerciseIndex = resultBean.getExerciseIndex(); //运动指数
                    List<WeatherBean.ResultBean.FutureBean> futureBeen = resultBean.getFuture();
                    mFutureBeanList.clear();
                    mFutureBeanList.addAll(futureBeen);
                    mWeatherAdapter.notifyDataSetChanged();

                    tvCity.setText(city);
                    tvWeather.setText(weather);
                    tvTmp.setText(temperature);
                    tvSunrise.setText(sunrise);
                    tvSunset.setText(sunset);
                    tvWind.setText(wind);
                    tvAirCondition.setText(airCondition);
                    tvDressingIndex.setText(dressingIndex);
                    tvColdIndex.setText(coldIndex);
                    String[] humiditySplit = humidity.split("：");
                    tvHumidity.setText(humiditySplit[1]);
                    tvPollutionIndex.setText(pollutionIndex);
                    tvWashIndex.setText(washIndex);
                    tvExerciseIndex.setText(exerciseIndex);
                }

            }

            @Override
            public void onError(API api, int i, Throwable throwable) {
                Logger.e(throwable.toString());
            }
        });
    }



    @OnClick(R.id.img_back)
    public void onViewClicked() {
        finish();
    }
}
