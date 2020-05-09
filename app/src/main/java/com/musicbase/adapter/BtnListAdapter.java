package com.musicbase.adapter;

import android.content.Context;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.musicbase.R;
import com.musicbase.entity.BtnListBean;

import java.util.List;

/**
 * Created by BAO on 2018-09-14.
 */

public class BtnListAdapter extends BaseQuickAdapter<BtnListBean, BaseViewHolder> {

    private Context context;
    public BtnListAdapter(Context context,@LayoutRes int layoutResId, @Nullable List<BtnListBean> data) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, BtnListBean item) {
        Glide.with(context).load(item.getIm()).into((ImageView) helper.getView(R.id.im));
        helper.setText(R.id.name, item.getName());
    }
}


