package com.lyb.customview;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.lyb.customview.touchview.DrawOwnView;

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
        drawOwnView=findViewById(R.id.draw_d);
        linearLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction()==MotionEvent.ACTION_MOVE){
                    //移动事件
                    //            RectF rectF=new RectF();
                    float x = event.getX();
                    float y = event.getY();
                    Log.i(TAG, "x== "+x);
                    Log.i(TAG, "y== "+y);
                    drawOwnView.setCenterXY(x,y);
                }
                return true;
//                return false;
            }
        });
    }
}