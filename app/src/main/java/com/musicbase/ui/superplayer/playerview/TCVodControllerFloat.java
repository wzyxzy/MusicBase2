package com.musicbase.ui.superplayer.playerview;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.musicbase.R;
import com.musicbase.ui.superplayer.SuperPlayerGlobalConfig;
import com.tencent.rtmp.ui.TXCloudVideoView;

import java.lang.reflect.Field;

/**
 * Created by liyuejiao on 2018/7/3.
 * <p>
 * 超级播放器悬浮窗控制界面
 */
public class TCVodControllerFloat extends TCVodControllerBase implements View.OnClickListener {

    private TXCloudVideoView floatVideoView;

    /**
     * 记录系统状态栏的高度
     */
    private static int statusBarHeight;

    /**
     * 记录当前手指位置在屏幕上的横坐标值
     */
    private float xInScreen;

    /**
     * 记录当前手指位置在屏幕上的纵坐标值
     */
    private float yInScreen;

    /**
     * 记录手指按下时在屏幕上的横坐标的值
     */
    private float xDownInScreen;

    /**
     * 记录手指按下时在屏幕上的纵坐标的值
     */
    private float yDownInScreen;

    /**
     * 记录手指按下时在小悬浮窗的View上的横坐标的值
     */
    private float xInView;

    /**
     * 记录手指按下时在小悬浮窗的View上的纵坐标的值
     */
    private float yInView;

    public TCVodControllerFloat(@NonNull Context context) {
        super(context);
        initViews();
    }

    public TCVodControllerFloat(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    public TCVodControllerFloat(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
    }

    private void initViews() {
        mLayoutInflater.inflate(R.layout.vod_controller_float, this);
        floatVideoView = (TXCloudVideoView) findViewById(R.id.float_cloud_video_view);
        ImageView ivClose = (ImageView) findViewById(R.id.iv_close);
        ivClose.setOnClickListener(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xInView = event.getX();
                yInView = event.getY();
                xDownInScreen = event.getRawX();
                yDownInScreen = event.getRawY() - getStatusBarHeight();
                xInScreen = event.getRawX();
                yInScreen = event.getRawY() - getStatusBarHeight();

                break;
            case MotionEvent.ACTION_MOVE:
                xInScreen = event.getRawX();
                yInScreen = event.getRawY() - getStatusBarHeight();
                // 手指移动的时候更新小悬浮窗的位置
                updateViewPosition();

                break;
            case MotionEvent.ACTION_UP:
                if (xDownInScreen == xInScreen && yDownInScreen == yInScreen) {
                    if (mVodController != null) {
                        mVodController.onRequestPlayMode(SuperPlayerConst.PLAYMODE_WINDOW);
                    }
                }
                break;
            default:
                break;
        }

        return true;
    }

    private int getStatusBarHeight() {
        if (statusBarHeight == 0) {
            try {
                Class<?> c = Class.forName("com.android.internal.R$dimen");
                Object o = c.newInstance();
                Field field = c.getField("status_bar_height");
                int x = (Integer) field.get(o);
                statusBarHeight = getResources().getDimensionPixelSize(x);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return statusBarHeight;
    }

    private void updateViewPosition() {
        int x = (int) (xInScreen - xInView);
        int y = (int) (yInScreen - yInView);
        SuperPlayerGlobalConfig.TXRect rect = SuperPlayerGlobalConfig.getInstance().floatViewRect;
        if (rect != null) {
            rect.x = x;
            rect.y = y;
        }
        mVodController.onFloatUpdate(x, y);
    }

    @Override
    void onShow() {

    }

    @Override
    void onHide() {

    }

//    @Override
//    void onTimerTicker() {
//
//    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_close:
                if (mVodController != null) {
                    mVodController.onBackPress(SuperPlayerConst.PLAYMODE_FLOAT);
                }
                break;
            default:
                break;
        }
    }

    public TXCloudVideoView getFloatVideoView() {
        return floatVideoView;
    }
}
