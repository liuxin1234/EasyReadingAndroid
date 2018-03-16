package com.liux.easyreading.activity.moreFunction;

import android.os.Bundle;
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
import com.mob.mobapi.apis.Dictionary;
import com.mob.tools.utils.ResHelper;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by
 * 项目名称：com.liux.easyreading.activity.moreFunction
 * 项目日期：2017/12/1
 * 作者：liux
 * 功能：新华字典API
 *
 * @author 75095
 */

public class DictionaryAPIActivity extends BaseActivity implements APICallback{


    @BindView(R.id.etDictionary)
    EditText etDictionary;
    @BindView(R.id.btnSearch)
    Button btnSearch;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvWubi)
    TextView tvWubi;
    @BindView(R.id.tvBushou)
    TextView tvBushou;
    @BindView(R.id.tvBihuaWithBushou)
    TextView tvBihuaWithBushou;
    @BindView(R.id.tvPinyin)
    TextView tvPinyin;
    @BindView(R.id.tvBrief)
    TextView tvBrief;
    @BindView(R.id.tvDetail)
    TextView tvDetail;
    @BindView(R.id.llResult)
    LinearLayout llResult;

    @Override
    protected void initToolbar(ToolbarHelper toolbarHelper) {
        toolbarHelper.setTitle("新华字典");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_dictionary;
    }

    @Override
    protected void initView() {

    }


    @OnClick(R.id.btnSearch)
    public void onViewClicked() {
        Dictionary api = (Dictionary) MobAPI.getAPI(Dictionary.NAME);
        api.queryDictionary(etDictionary.getText().toString().trim(), this);
    }

    @Override
    public void onSuccess(API api, int i, Map<String, Object> result) {
        HashMap<String, Object> res = ResHelper.forceCast(result.get("result"));
        tvName.setText(ResHelper.toString(res.get("name")));
        tvPinyin.setText(ResHelper.toString(res.get("pinyin")));
        tvWubi.setText(ResHelper.toString(res.get("wubi")));
        tvBushou.setText(ResHelper.toString(res.get("bushou")));
        tvBihuaWithBushou.setText(ResHelper.toString(res.get("bihuaWithBushou")));
        tvBrief.setText(ResHelper.toString(res.get("brief")));
        tvDetail.setText(ResHelper.toString(res.get("detail")));
    }

    @Override
    public void onError(API api, int i, Throwable details) {
        details.printStackTrace();
        Toast.makeText(this, R.string.error_raise, Toast.LENGTH_SHORT).show();
    }
}
