package com.lyb.customview.ui;

import android.os.Bundle;

import com.lyb.customview.R;
import com.lyb.customview.RadarView.RadarView;

import androidx.appcompat.app.AppCompatActivity;

public class RadarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radar);
        RadarView radarView = findViewById(R.id.radar_view);
        radarView.startRippleAnimation();
    }
}