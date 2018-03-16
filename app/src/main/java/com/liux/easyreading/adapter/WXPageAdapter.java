package com.liux.easyreading.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.liux.easyreading.R;
import com.liux.module.wxBean.ArticleSearchBean;

import java.util.List;

/**
 * Created by
 * 项目名称：com.liux.easyreading.adapter
 * 项目日期：2017/11/3
 * 作者：liux
 * 功能：
 */

public class WXPageAdapter extends BaseQuickAdapter<ArticleSearchBean.ResultBean.ListBean,BaseViewHolder> {
    private Context mContext;


    public WXPageAdapter(Context context,@LayoutRes int layoutResId, @Nullable List<ArticleSearchBean.ResultBean.ListBean> data) {
        super(layoutResId, data);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, ArticleSearchBean.ResultBean.ListBean item) {
        helper.setText(R.id.tv_title,item.getTitle())
                .setText(R.id.tv_pubTime,item.getPubTime());
        Glide.with(mContext)
                .load(item.getThumbnails())
                .into((ImageView) helper.getView(R.id.img_View));
    }
}
