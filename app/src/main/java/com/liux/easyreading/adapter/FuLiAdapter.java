package com.liux.easyreading.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.liux.easyreading.R;
import com.liux.module.GankBean.BaseBean;
import com.liux.module.GankBean.ResultBean;
import com.liux.module.wxBean.CategoriesBean;

import java.util.List;

import static com.liux.easyreading.R.*;
import static com.liux.easyreading.R.drawable.*;

/**
 * Created by
 * 项目名称：com.liux.easyreading.adapter
 * 项目日期：2017/12/26
 * 作者：liux
 * 功能：
 *
 * @author 750954283(qq)
 */

public class FuLiAdapter extends BaseQuickAdapter<ResultBean,BaseViewHolder> {
    private Context mContext;

    public FuLiAdapter(Context context,@LayoutRes int layoutResId, @Nullable List<ResultBean> data) {
        super(layoutResId, data);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, ResultBean item) {
        ImageView imageView = helper.getView(id.image_id);
        ViewGroup.LayoutParams params = imageView.getLayoutParams();
        params.height = (int) (300+Math.random()*200);
        imageView.setLayoutParams(params);
        //设置Glide加载图片时候的配置，如正在加载图片，图片加载错误等等
        RequestOptions options = new RequestOptions();
        options.centerCrop()
                .placeholder(R.drawable.ic_loading)
                .error(R.drawable.ic_load_fail)
                .fallback(R.drawable.ic_load_fail);
        Glide.with(mContext)
                .load(item.getUrl())
                .apply(options)
                .into(imageView);

    }
}
