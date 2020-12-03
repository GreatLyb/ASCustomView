package com.lyb.customview;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.lyb.customview.touchview.DrawOwnView;
import com.lyb.customview.ui.RadarActivity;
import com.lyb.customview.ui.RingProgressBarActivity;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Lyb";
    DrawOwnView drawOwnView;
    private LinearLayout linearLayout;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linearLayout=findViewById(R.id.llayout_root);
    }

    public void RadarView(View view) {
        startActivity(new Intent(this, RadarActivity.class ));
    }

    public void RingBar(View view) {
        startActivity(new Intent(this, RingProgressBarActivity.class ));
    }
}