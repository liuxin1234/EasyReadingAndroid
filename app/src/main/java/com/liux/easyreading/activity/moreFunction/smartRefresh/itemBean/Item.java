package com.liux.easyreading.activity.moreFunction.smartRefresh.itemBean;

import android.support.annotation.StringRes;

import com.liux.easyreading.R;
import com.liux.easyreading.activity.moreFunction.smartRefresh.BezierStyleActivity;
import com.liux.easyreading.activity.moreFunction.smartRefresh.CircleStyleActivity;
import com.liux.easyreading.activity.moreFunction.smartRefresh.ClassicsStyleActivity;
import com.liux.easyreading.activity.moreFunction.smartRefresh.DeliveryStyleActivity;
import com.liux.easyreading.activity.moreFunction.smartRefresh.DropBoxStyleActivity;
import com.liux.easyreading.activity.moreFunction.smartRefresh.FlyRefreshStyleActivity;
import com.liux.easyreading.activity.moreFunction.smartRefresh.FunGameBattleCityStyleActivity;
import com.liux.easyreading.activity.moreFunction.smartRefresh.FunGameHitBlockStyleActivity;
import com.liux.easyreading.activity.moreFunction.smartRefresh.MaterialStyleActivity;
import com.liux.easyreading.activity.moreFunction.smartRefresh.PhoenixStyleActivity;
import com.liux.easyreading.activity.moreFunction.smartRefresh.StoreHouseStyleActivity;
import com.liux.easyreading.activity.moreFunction.smartRefresh.TaurusStyleActivity;
import com.liux.easyreading.activity.moreFunction.smartRefresh.WaterDropStyleActivity;
import com.liux.easyreading.activity.moreFunction.smartRefresh.WaveSwipeStyleActivity;

/**
 * Created by
 * 项目名称：com.liux.easyreading.activity.moreFunction.smartRefresh
 * 项目日期：2017/12/6
 * 作者：liux
 * 功能：
 */

public enum Item {
    Elivery(R.string.title_activity_style_delivery,DeliveryStyleActivity.class),
    Dropbox(R.string.title_activity_style_dropbox, DropBoxStyleActivity.class),
    FlyRefresh(R.string.title_activity_style_flyrefresh, FlyRefreshStyleActivity.class),
    WaveSwipe(R.string.title_activity_style_wave_swip, WaveSwipeStyleActivity.class),
    WaterDrop(R.string.title_activity_style_water_drop, WaterDropStyleActivity.class),
    Material(R.string.title_activity_style_material, MaterialStyleActivity.class),
    Phoenix(R.string.title_activity_style_phoenix, PhoenixStyleActivity.class),
    Taurus(R.string.title_activity_style_taurus, TaurusStyleActivity.class),
    Bezier(R.string.title_activity_style_bezier, BezierStyleActivity.class),
    Circle(R.string.title_activity_style_circle,CircleStyleActivity.class),
    FunGameHitBlock(R.string.title_activity_style_fungame_hitblock, FunGameHitBlockStyleActivity.class),
    FunGameBattleCity(R.string.title_activity_style_fungame_battlecity, FunGameBattleCityStyleActivity.class),
    StoreHouse(R.string.title_activity_style_storehouse, StoreHouseStyleActivity.class),
    Classics(R.string.title_activity_style_classics, ClassicsStyleActivity.class),
    ;
    public int nameId;
    public Class<?> clazz;
    Item(@StringRes int nameId, Class<?> clazz){
        this.nameId = nameId;
        this.clazz = clazz;
    }
}
