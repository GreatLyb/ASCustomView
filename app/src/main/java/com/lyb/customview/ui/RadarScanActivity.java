package com.lyb.customview.ui;

import android.os.Bundle;
import android.view.View;

import com.lyb.customview.R;
import com.lyb.customview.RadarView.RadarScanningView;

import androidx.appcompat.app.AppCompatActivity;

public class RadarScanActivity extends AppCompatActivity {
    RadarScanningView radarScanningView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radar_scan);
        radarScanningView = findViewById(R.id.radar_scan_view);
    }

    /**
     * 开始涟漪
     *
     * @param view
     */
    public void startRipple(View view) {
        radarScanningView.start();
    }

    public void stopRipple(View view) {
        radarScanningView.stop();
    }
}