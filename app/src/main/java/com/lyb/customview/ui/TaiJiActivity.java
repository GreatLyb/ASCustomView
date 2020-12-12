package com.lyb.customview.ui;

import android.os.Bundle;
import android.view.View;

import com.lyb.customview.R;
import com.lyb.customview.taiji.TaiJiView;

import androidx.appcompat.app.AppCompatActivity;

public class TaiJiActivity extends AppCompatActivity {
    private TaiJiView taiJiView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tai_ji);
        taiJiView=findViewById(R.id.taiji);
    }

    /**
     * 开始选择
     *
     * @param view
     */
    public void startRotation(View view) {
        taiJiView.startRotation();
    }

    /**
     * 暂停
     *
     * @param view
     */
    public void pauseRotation(View view) {
        taiJiView.pauseRotation();

    }

    /**
     * 继续
     *
     * @param view
     */
    public void restartRotation(View view) {
        taiJiView.reStartRotation();

    }

    /**
     * 停止
     * @param view
     */
    public void stopRotation(View view) {
        taiJiView.stopRotation();
    }
}