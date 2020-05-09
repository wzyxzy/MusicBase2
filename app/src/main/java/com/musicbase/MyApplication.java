package com.musicbase;

import android.app.Application;
import android.util.Log;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.musicbase.preferences.Preferences;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;

import org.xutils.x;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by BAO on 2018-09-13.
 */

public class MyApplication extends Application {

    public static Application instance;

    public static IWXAPI mWxApi;

    public static Application getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {

        super.onCreate();
        instance = this;
        JPushInterface.setDebugMode(false);
        JPushInterface.init(this);

        initOkgo();
//        CrashHandler crashHandler = CrashHandler.getInstance();
//        crashHandler.init(getApplicationContext());

        mWxApi = WXAPIFactory.createWXAPI(this, Preferences.WX_APP_ID, false);
        mWxApi.registerApp(Preferences.WX_APP_ID);
//        JPushInterface.setDebugMode(false); // 设置开启日志,发布时请关闭日志
//        JPushInterface.init(this);
        // 开启debug模式，方便定位错误，具体错误检查方式可以查看http://dev.umeng.com/social/android/quick-integration的报错必看，正式发布，请关闭该模式
//        Config.DEBUG = false;
//        Config.isJumptoAppStore = true;
//        UMShareAPI.get(this);
        // 初始化SDK
        UMConfigure.init(this, "5c5269e4b465f56085000132", "index", UMConfigure.DEVICE_TYPE_PHONE, null);
        // 选用AUTO页面采集模式
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);

        x.Ext.init(this);
        Logger.addLogAdapter(new AndroidLogAdapter() {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  //（可选）是否显示线程信息。 默认值为true
                .methodCount(2)         // （可选）要显示的方法行数。 默认2
                .methodOffset(7)        // （可选）隐藏内部方法调用到偏移量。 默认5
                .tag("PNDOO")   //（可选）每个日志的全局标记。 默认PRETTY_LOGGER
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));

    }

    private void initOkgo() {
        OkGo.getInstance().init(this).setCacheMode(CacheMode.REQUEST_FAILED_READ_CACHE);
    }

    //各个平台的配置，建议放在全局Application或者程序入口
//    {
//        PlatformConfig.setWeixin("wxb5c69e77124b6557", "d7cf4e1d3096f8cbfbfa7f91dcd7916c");
//        PlatformConfig.setSinaWeibo("491304448", "f6192e4455dc39a492cd523ccdab8c60","http://www.pndoo.com");
//        PlatformConfig.setQQZone("1105281436", "O5SPjXocx1QWTdsf");
//    }
}
