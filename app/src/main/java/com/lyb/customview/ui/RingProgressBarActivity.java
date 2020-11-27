package com.lyb.customview.ui;

import android.os.Bundle;

import com.lyb.customview.R;
import com.lyb.customview.progressbar.RingProgressBar;

import androidx.appcompat.app.AppCompatActivity;

public class RingProgressBarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ring_progress_bar);
        RingProgressBar ringProgressBar=findViewById(R.id.progress_bar);
        ringProgressBar.setDurationTime(3000);
        ringProgressBar.setProgress(100);
    }
}