package com.musicbase.adapter;

import android.content.Context;
import androidx.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.musicbase.R;
import com.musicbase.entity.DetailBean;
import com.musicbase.preferences.Preferences;

import java.util.List;

public class ModuleAdapter extends BaseQuickAdapter<DetailBean.DataBean.ModuleList, BaseViewHolder> {
    private DetailBean.DataBean dataBean;

    private Context context;

    public ModuleAdapter(int layoutResId, @Nullable List<DetailBean.DataBean.ModuleList> data, DetailBean.DataBean dataBean, Context context) {
        super(layoutResId, data);
        this.dataBean = dataBean;
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, DetailBean.DataBean.ModuleList item) {
        helper.setText(R.id.name1, item.getModuleName());
        try {
            String path = (String) dataBean.getClass().getDeclaredField(item.getModuleField()).get(dataBean);
            if (item.getModuleField().equalsIgnoreCase("catalogueImgPath")) {
                helper.setVisible(R.id.text1, false).setVisible(R.id.image1, true);
                Glide.with(context).load(Preferences.IMAGE_HTTP_LOCATION + path).into((ImageView) helper.getView(R.id.image1));
            } else {
                helper.setVisible(R.id.text1, true).setVisible(R.id.image1, false);
                helper.setText(R.id.text1, path);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

    }
}
