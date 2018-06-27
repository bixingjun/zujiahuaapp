package com.example.shiping.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.shiping.R;
import com.example.shiping.view.CustomVideoView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RelativeLayout rl = (RelativeLayout)findViewById(R.id.rl);

        CustomVideoView customVideoView = new CustomVideoView(this, rl);
        customVideoView.setDataSource("http://fairee.vicp.net:83/2016rm/0116/baishi160116.mp4");
        customVideoView.addView(customVideoView);
    }
}
