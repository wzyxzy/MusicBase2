package com.musicbase.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.musicbase.R;
import com.musicbase.entity.UpdateBean;
import com.musicbase.preferences.Preferences;
import com.musicbase.util.ActivityUtils;
import com.orhanobut.logger.Logger;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Type;

public class UpdateActivity extends Activity {
    LinearLayout ll_content;
    TextView tv_updateInfo;
    TextView tv_updateTitle;
    Button btn_update;
    UpdateBean.Data update;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_update);
        initView();
    }

    public void initView() {
        initTitle();
        ll_content = (LinearLayout) findViewById(R.id.ll_content);
        tv_updateInfo = (TextView) findViewById(R.id.tv_updateInfo);
        tv_updateTitle = (TextView) findViewById(R.id.tv_updatetitle);
        btn_update = (Button) findViewById(R.id.btn_update);
        update = (UpdateBean.Data) getIntent().getExtras().getSerializable("update");
        Logger.d(update);
        if (update.getIsUpdate().equals("3")) {
            getVerisonUpdate();
        } else {
            setUpgradeInfo(update);
        }


    }

    private void setUpgradeInfo(final UpdateBean.Data bean) {
        if (bean.getIsUpdate().equals("0")) {
            tv_updateTitle.setText("当前已是新版本");
            tv_updateInfo.setVisibility(View.INVISIBLE);
            btn_update.setVisibility(View.INVISIBLE);
        } else {
            tv_updateTitle.setText("版本更新：");
            tv_updateInfo.setVisibility(View.VISIBLE);
            btn_update.setVisibility(View.VISIBLE);
            tv_updateInfo.setText(TextUtils.isEmpty(bean.getDesc()) ? "" : bean.getDesc().replace("$", "\n"));
            btn_update.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri uri = Uri.parse(bean.getUrl());
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }
            });
        }
    }

    private void initTitle() {
        ((TextView) findViewById(R.id.titlelayout_title)).setText("版本更新");
        ((ImageButton) findViewById(R.id.titlelayout_back)).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            this.finish();
        }
        return true;
    }


    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

    private void getVerisonUpdate() {

        RequestParams params = new RequestParams(Preferences.VERISON_UPDATE_URL);
        params.addQueryStringParameter("version", getClientVersion());
        params.addQueryStringParameter("systemType", "android");

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                Logger.d("getVerisonUpdate=========" + s);
                Gson gson = new Gson();
                Type type = new TypeToken<UpdateBean>() {
                }.getType();
                UpdateBean bean = gson.fromJson(s, type);
                if (bean != null) {
                    setUpgradeInfo(bean.getData());
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                Logger.d("throwable=" + throwable.getMessage());
                ActivityUtils.showToast(UpdateActivity.this, getString(R.string.internet_fail));
            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private String getClientVersion() {
        try {
            PackageManager packageManager = this.getPackageManager();
            PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(), 0);
            return packInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return "1.0.0";
        }
    }

}
