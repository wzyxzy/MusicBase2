package com.musicbase.adapter;

import android.content.Context;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import android.text.TextUtils;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.musicbase.R;
import com.musicbase.download2.db.DataBaseFiledParams;
import com.musicbase.download2.db.DataBaseHelper;
import com.musicbase.download2.entity.DocInfo;
import com.musicbase.entity.DetailBean;
import com.musicbase.util.ActivityUtils;

import java.util.List;

/**
 * Created by BAO on 2018-09-14.
 */

public class DetailAdapter extends BaseQuickAdapter<DetailBean.DataBean.ResourceListBean, BaseViewHolder> {

    private Context context;

    public DetailAdapter(Context context, @LayoutRes int layoutResId, @Nullable List<DetailBean.DataBean.ResourceListBean> data) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, DetailBean.DataBean.ResourceListBean item) {
        int imageId = 0;
        switch (item.getResourceType()) {
            case "cloudVideo":
                imageId = R.mipmap.icon_video;
                break;
            case "cloudAudio":
                imageId = R.mipmap.icon_audio;
                break;
            case "testPaper":
                imageId = R.mipmap.icon_exercises;
                break;
            case "courseware":
            case "url":
                imageId = R.mipmap.icon_courseware;
                break;
        }
        if (item.getIsFolder() == 1) {
            imageId = R.mipmap.icon_folder;
        }
        helper.setImageResource(R.id.im, imageId);
        helper.setText(R.id.content, item.getResourceFileName());
        if (item.getIsPay() == 1 && item.getCurrentPrice().equals("0.00")) {
            helper.setVisible(R.id.free, false);

        } else if (item.getIsPay() == 1) {
            helper.setVisible(R.id.free, true);
            TextView free = helper.getView(R.id.free);
            free.setText("  ¥ " + item.getCurrentPrice() + "  ");

        } else {
            helper.setVisible(R.id.free, false);

            if (ActivityUtils.isExistByName(context, item.getResourceId() + "", item.getResourceSaveName())) {
                helper.setVisible(R.id.nodownload, false);
            } else {
                if ((item.getResourceType().equals("testPaper") || item.getResourceType().equalsIgnoreCase("mp3") || item.getResourceType().equals("courseware")) && TextUtils.isEmpty(item.getOnlineLinks()))
                    helper.setVisible(R.id.nodownload, true);
            }
            helper.setText(R.id.progress, "");
            DataBaseHelper dbhelper = new DataBaseHelper(context);
            List<DocInfo> infos = dbhelper.getInfo2(item.getResourceId() + "");
            for (DocInfo docInfo : infos) {
                if (item.getResourceSaveName().equals(docInfo.getName())) {
                    if (docInfo.getStatus() == DataBaseFiledParams.PAUSING) {
                        helper.setText(R.id.progress, "暂停");
                    } else if (docInfo.getStatus() == DataBaseFiledParams.WAITING) {
                        helper.setText(R.id.progress, "等待中");
                    } else if (docInfo.getStatus() == DataBaseFiledParams.FAILED) {
                        helper.setText(R.id.progress, "服务器忙");
                    } else {
                        helper.setText(R.id.progress, docInfo.getDownloadProgress() + "%");
                        if (docInfo.getDownloadProgress() == 100) {
                            helper.setText(R.id.progress, "");
                        }
                    }
                }
            }
        }
    }
}


