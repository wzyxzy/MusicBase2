package com.musicbase.ui.activity;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.musicbase.R;
import com.musicbase.ui.view.ProgressWebview;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint({"NewApi", "SetJavaScriptEnabled"})
public class BrowerActivity extends Activity {

    @BindView(R.id.titlelayout_back)
    ImageButton titlelayoutBack;
    @BindView(R.id.titlelayout_title)
    TextView titlelayoutTitle;
    @BindView(R.id.wv_brower)
    ProgressWebview mWebView;
    private String filepath = "http://www.pndoo.com/link_list.html";

    int sum = 0;// 记录进去页面次数，为1时，退出
    private final String TAG = getClass().getSimpleName();
    private TextView tv_header_title, close;
    private LinearLayout title_layout, ll_header;
    private String name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_brower);
        ButterKnife.bind(this);
        if (getIntent().getStringExtra("filePath") != null && (!getIntent().getStringExtra("filePath").equals(""))) {
            filepath = getIntent().getStringExtra("filePath");
            Logger.d(filepath);
        }
        if (getIntent().getStringExtra("name") != null && (!getIntent().getStringExtra("name").equals(""))) {
            name = getIntent().getStringExtra("name");
        }

        initView();
    }

    public void initView() {
        titlelayoutTitle.setText(name);
        titlelayoutBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

//        int wv_brower = getResources().getIdentifier("wv_brower", "id", getPackageName());
//        mWebView = (ProgressWebview) findViewById(wv_brower);
        WebSettings ws = mWebView.getSettings();

        ws.setJavaScriptEnabled(true);
        ws.setAllowFileAccess(true);
        ws.setAllowFileAccessFromFileURLs(true);
        ws.setAllowUniversalAccessFromFileURLs(true);
        ws.setCacheMode(WebSettings.LOAD_NO_CACHE);
        // 是否允许缩放
        ws.setBuiltInZoomControls(true);
        ws.setSupportZoom(true);
        ws.setDomStorageEnabled(true);
        ws.setAppCacheEnabled(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ws.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        mWebView.loadUrl(filepath);

        if (!filepath.startsWith("https://www.bilibili.com") && !filepath.startsWith("https://www.ximalaya.com") && !filepath.startsWith("https://www.yinyuesuyang.com/update.html")) {
            mWebView.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                    String url = "";
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        url = request.getUrl().toString();
                    } else {
                        url = request.toString();
                    }
                    Logger.d(url);

                    return false;
//                Logger.d(url);
//                    view.loadUrl(url);
////                else
////                    ActivityUtils.showToast(WorkWebActivity.this, "非音基应用选项，不可操作！");
                }

//                    @Override
//                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                        return "bilibili".equalsIgnoreCase(Uri.parse(url).getScheme());
//
//                    }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mWebView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mWebView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void btnBack(View v) {
        if (mWebView.canGoBack()) {
            mWebView.goBack();// 返回上一页面
            // Log.d(TAG, mWebView.getUrl());
        } else {
            // System.exit(0);//退出程序
            finish();
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mWebView.canGoBack()) {
                mWebView.goBack();// 返回上一页面
                // Log.d(TAG, mWebView.getUrl());
                return true;
            } else {
                // System.exit(0);//退出程序
                finish();
                return true;
            }
        }
        return false;
    }
}
