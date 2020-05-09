package com.musicbase.ui.activity;

import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.VideoView;

import com.musicbase.R;
import com.orhanobut.logger.Logger;


public class VideoActivity extends AppCompatActivity {

    private VideoView video_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        initView();
    }

    private void initView() {
        video_view = (VideoView) findViewById(R.id.video_view);
        Logger.d(getIntent().getStringExtra("Video_url"));
        video_view.setVideoURI(Uri.parse(getIntent().getStringExtra("Video_url")));
        video_view.start();
    }
}
