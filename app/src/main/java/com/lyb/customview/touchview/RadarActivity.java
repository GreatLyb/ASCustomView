package com.lyb.customview.touchview;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;

import com.lyb.customview.R;
import com.lyb.customview.RadarView.RadarView;

import androidx.appcompat.app.AppCompatActivity;

public class RadarActivity extends AppCompatActivity {
    RadarView radarView;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //修改了文件
        setContentView(R.layout.activity_radar);
//        radarView = findViewById(R.id.radar_view);
        //---------------------------dev_1.0.1--
        //---------------------------local_1.0.2
        countDownTimer = new CountDownTimer(300 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.i("Lyb","onTick=="+millisUntilFinished);
            }

            @Override
            public void onFinish() {
                Log.i("Lyb","onFinish==");

            }
        }.start();
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

    @Override
    public void finish() {
        Log.i("Lyb","finish==");
        if (countDownTimer!=null){
            Log.i("Lyb","finishtDownTimer==");
            countDownTimer.cancel();
        }
        super.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    public void reStartRipple(View view) {
        radarView.resumeRippleAnimation();
    }
}