package com.liux.easyreading.activity.moreFunction;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.liux.easyreading.R;
import com.liux.easyreading.activity.moreFunction.smartRefresh.itemBean.Item;
import com.liux.easyreading.adapter.SmartRefreshAdapter;
import com.liux.easyreading.base.BaseActivity;
import com.liux.easyreading.utils.GsonUtils;
import com.liux.easyreading.utils.ToastUtil;
import com.liux.easyreading.view.DividerItemDecoration;
import com.liux.easyreading.view.ToolbarHelper;
import com.liux.module.smartRefreshBean.SmartRefreshBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static android.R.layout.simple_list_item_2;

/**
 * Created by
 * 项目名称：com.liux.easyreading.activity.moreFunction
 * 项目日期：2017/12/5
 * 作者：liux
 * 功能：Android智能下拉刷新框架-SmartRefreshLayout
 *
 * @author 75095
 */

public class SmartRefreshLayoutActivity extends BaseActivity {


    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.tv_Empty)
    TextView tvEmpty;
    @BindView(R.id.recycle_View)
    RecyclerView recycleView;

    private boolean isFirst = true;

    private SmartRefreshAdapter mSmartRefreshAdapter;

    private List<SmartRefreshBean> mRefreshBeanList = new ArrayList<>();

    @Override
    protected void initToolbar(ToolbarHelper toolbarHelper) {
        toolbarHelper.setTitle("智能刷新");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_smart_refresh_layout;
    }

    @Override
    protected void initView() {
        initData();
        initRefresh();
        initAdapter();
    }

    private void initData() {
        mRefreshBeanList.clear();
        mRefreshBeanList.add(new SmartRefreshBean("Delivery","气球快递"));
        mRefreshBeanList.add(new SmartRefreshBean("Dropbox","掉落盒子"));
        mRefreshBeanList.add(new SmartRefreshBean("FlyRefresh","纸飞机"));
        mRefreshBeanList.add(new SmartRefreshBean("WaveSwipe","全屏水波"));
        mRefreshBeanList.add(new SmartRefreshBean("WaterDrop","苹果水滴"));
        mRefreshBeanList.add(new SmartRefreshBean("Material","官方主题"));
        mRefreshBeanList.add(new SmartRefreshBean("Phoneix","金色校园"));
        mRefreshBeanList.add(new SmartRefreshBean("Taurus","冲上云霄"));
        mRefreshBeanList.add(new SmartRefreshBean("Bezier","贝塞尔雷达"));
        mRefreshBeanList.add(new SmartRefreshBean("Circle","弹出圆圈"));
        mRefreshBeanList.add(new SmartRefreshBean("FunGameHitBlock","打砖块"));
        mRefreshBeanList.add(new SmartRefreshBean("FunGameBattleCity","战争城市"));
        mRefreshBeanList.add(new SmartRefreshBean("StoreHouse","StoreHouse"));
        mRefreshBeanList.add(new SmartRefreshBean("Classics","经典风格"));
        GsonUtils.toJson(mRefreshBeanList);
    }

    private void initAdapter() {
        mSmartRefreshAdapter = new SmartRefreshAdapter(simple_list_item_2,mRefreshBeanList);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        recycleView.addItemDecoration(new DividerItemDecoration(this,1));
        recycleView.setAdapter(mSmartRefreshAdapter);
        mSmartRefreshAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(SmartRefreshLayoutActivity.this, Item.values()[position].clazz);
                startActivity(intent);
                ToastUtil.showToast(SmartRefreshLayoutActivity.this,""+Item.values()[position].name());
            }
        });
    }

    private void initRefresh() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                if (isFirst) {
                    tvEmpty.setVisibility(View.VISIBLE);
                    isFirst = false;
                } else {
                    tvEmpty.setVisibility(View.GONE);
                    initData();
                }
                refreshlayout.finishRefresh(3000);
            }
        });

        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadmore(3000);
            }
        });

    }


}
