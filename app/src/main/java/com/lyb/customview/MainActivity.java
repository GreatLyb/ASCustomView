package com.lyb.customview;

import android.os.Bundle;

import com.lyb.customview.progressbar.RingProgressBar;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    RingProgressBar ringProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         ringProgressBar=findViewById(R.id.progress_bar);
        ringProgressBar.setDurationTime(8000);
        ringProgressBar.setProgress(100);
//        ringProgressBar.setProgress(ringProgressBar.getProgress()+5);
    }
}