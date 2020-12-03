package com.lyb.customview.ui;

import android.os.Bundle;
import android.view.View;

import com.lyb.customview.R;
import com.lyb.customview.RadarView.RadarView;

import androidx.appcompat.app.AppCompatActivity;

public class RadarActivity extends AppCompatActivity {
    RadarView radarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radar);
//        radarView = findViewById(R.id.radar_view);

    }

    /**
     * 开始涟漪
     *
     * @param view
     */
    public void startRipple(View view) {
        radarView.startRippleAnimation();
    }

    public void stopRipple(View view) {
        radarView.stopRippleAnimation();
    }

    public void pauseRipple(View view) {
        radarView.pauseRippleAnimation();

    }

    public void reStartRipple(View view) {
        radarView.resumeRippleAnimation();
    }
}