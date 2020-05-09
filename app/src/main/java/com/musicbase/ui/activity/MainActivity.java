package com.musicbase.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.musicbase.BaseActivity;
import com.musicbase.R;
import com.musicbase.adapter.MyViewPagerAdapter;
import com.musicbase.ui.fragment.BuyedFragment;
import com.musicbase.ui.fragment.ExamFragment;
import com.musicbase.ui.fragment.FirstFragment;
import com.musicbase.ui.fragment.MeFragment;
import com.musicbase.ui.view.CustomViewpager;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends BaseActivity {

    private Fragment f_first, f_buyed, f_exam, f_me;
    private CustomViewpager viewpager;
    private MyViewPagerAdapter adapter;
    private List<Fragment> list = new ArrayList<Fragment>();
    private int current_activity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initView();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        int id = getIntent().getIntExtra("mainFlag", 0);
        final int courseId = getIntent().getIntExtra("courseId", 0);
        final int systemCodeId = getIntent().getIntExtra("systemCodeId", 0);

        if (id == 1) {
            changeClickToNormal(current_activity);
            setContent(2);
            if (courseId == 0 && systemCodeId == 0)
                return;

            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    Intent intent2 = new Intent(MainActivity.this, DetailActivity.class);
                    intent2.putExtra("courseId", courseId);
                    intent2.putExtra("systemCodeId", systemCodeId);
                    intent2.putExtra("buyed", true);
                    startActivity(intent2);
                }
            }, 500);

        }
    }

    private void initView() {
        f_first = new FirstFragment();
        f_buyed = new BuyedFragment();
        f_exam = new ExamFragment();
        f_me = new MeFragment();
        ((MeFragment) f_me).setListener(new MainListener() {

            @Override
            public void onFinish() {
                // TODO Auto-generated method stub
                finish();
            }
        });
        list.add(f_first);
        list.add(f_buyed);
        list.add(f_exam);
        list.add(f_me);
        viewpager = (CustomViewpager) findViewById(R.id.viewpager);
        viewpager.setOffscreenPageLimit(4);
        adapter = new MyViewPagerAdapter(getSupportFragmentManager(), list);
        viewpager.setAdapter(adapter);

    }

    // 点击状态变成正常状态
    private void changeClickToNormal(int current_activity) {
        LinearLayout ll = (LinearLayout) findViewById(getResources().getIdentifier("nav0" + current_activity, "id", "com.music.base"));
        // ll.setBackgroundResource(R.drawable.bg_tab_normal);
        ImageView iv = (ImageView) ll.getChildAt(0);
        iv.setImageResource(getResources().getIdentifier("tab_normal_image" + current_activity, "mipmap", "com.music.base"));
        TextView tv = (TextView) ll.getChildAt(1);
        tv.setTextColor(this.getResources().getColor(R.color.grey_666666));
    }

    // 点击状态变成正常状态
    private void changeNormalToClick(int current_activity) {
        LinearLayout ll = (LinearLayout) findViewById(getResources().getIdentifier("nav0" + current_activity, "id", "com.music.base"));
        // ll.setBackgroundResource(R.drawable.bg_tab_click);
        ImageView iv = (ImageView) ll.getChildAt(0);
        iv.setImageResource(getResources().getIdentifier("tab_click_image" + current_activity, "mipmap", "com.music.base"));
        TextView tv = (TextView) ll.getChildAt(1);
        tv.setTextColor(this.getResources().getColor(R.color.red_e61b19));
    }

    // 导航栏按键控制
    public void btnNavOnclick(View v) {
        int id = v.getId();
        if (id == R.id.nav01) {
            changeClickToNormal(current_activity);
            setContent(1);
        } else if (id == R.id.nav02) {
            changeClickToNormal(current_activity);
            setContent(2);
        } else if (id == R.id.nav03) {
            changeClickToNormal(current_activity);
            setContent(3);
        } else if (id == R.id.nav04) {
            changeClickToNormal(current_activity);
            setContent(4);
        } else if (id == R.id.nav_music) {
            startActivity(new Intent(this, MusicActivity.class));
        }

    }

    public void setContent(int current_activity) {
        viewpager.setCurrentItem(current_activity - 1, false);
        changeNormalToClick(current_activity);
        this.current_activity = current_activity;
        // Intent intent=new Intent(getBaseContext(),BookActivity00.class);
        // startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    long exitTime = 0;

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {

        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
//            if (current_activity == 4 && ((Fragment_Browse) f_browse).canGoBack()) {
//                ((Fragment_Browse) f_browse).goBack();
//                return true;
//            }
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                // SPUtility.putSPString(getApplicationContext(), "isPlay",
                // "false");
                // this.stopService(new Intent(this, MusicPlayerService.class));
                finish();
                // System.exit(0);
            }
            return true;
        }

        return super.dispatchKeyEvent(event);
    }

    public interface MainListener {
        void onFinish();
    }


}
