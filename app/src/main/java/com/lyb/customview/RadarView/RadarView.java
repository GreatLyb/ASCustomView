package com.lyb.customview.RadarView;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.RelativeLayout;

import com.lyb.customview.R;

import java.util.ArrayList;

import androidx.annotation.FloatRange;
import androidx.annotation.IntRange;
import androidx.annotation.Nullable;

/**
 * ASCustomView
 *
 * @Description： 自定义雷达view
 * @Date : 2020-11-27 10:58
 * @Author： Lyb
 */
public class RadarView extends RelativeLayout {
    private static final String TAG = "Lyb";
    /**
     * 涟漪从开始到结束的动画持续时间
     */
    private @IntRange(from = 0)
    int rippleDurationTime = 3000;
    /**
     * 涟漪的数量
     */
    private @IntRange(from = 0)
    int rippleAmount = 4;
    /**
     * 涟漪的线宽
     */

    private @FloatRange(from = 0)
    float rippleStrokeWidth = 1;
    /**
     * 涟漪的画笔
     */
    private Paint ripplePaint;
    /**
     * 1. 填充 2.边框
     */
    private @IntRange(from = 1, to = 2)
    int rippleType = 1;
    /**
     * 圆的半径
     */
    private @FloatRange(from = 0)
    float rippleRadius = 50;//半径
    private ArrayList<RippleView> rippleViewList = new ArrayList<>();
    /**
     * 动画的集合
     */
    private ArrayList<Animator> animatorList;
    /**
     * 动画集
     */
    private AnimatorSet animatorSet;
    /**
     * 动画的状态
     */

    private @IntRange(from = 0, to = 2)
    int rippleAnimatorState = 0;//0 未开始  1.进行中 2 暂停状态
    /**
     * 动画的状态
     */
    private  int rippleColor;//0 未开始  1.进行中 2 暂停状态

    private @FloatRange(from = 0)
    float scaleXStart = 1f;
    private @FloatRange(from = 0)
    float scaleXEnd = 7f;
    private @FloatRange(from = 0)
    float scaleYStart = 1f;
    private @FloatRange(from = 0)
    float scaleYEnd = 7f;
    private @FloatRange(from = 0)
    float alphaStart = 1.0f;
    private @FloatRange(from = 0)
    float alphaEnd = 0f;

    public RadarView(Context context) {
        this(context, null);
    }

    public RadarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RadarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RadarView_style);
        rippleDurationTime = typedArray.getInteger(R.styleable.RadarView_style_rippleDurationTime, 3000);
        rippleAmount = typedArray.getInteger(R.styleable.RadarView_style_rippleAmount, 4);
        rippleType = typedArray.getInteger(R.styleable.RadarView_style_rippleType, 1);
        rippleRadius = typedArray.getInteger(R.styleable.RadarView_style_rippleRadius, 50);
        rippleColor = typedArray.getColor(R.styleable.RadarView_style_rippleColor, Color.RED);

        scaleXStart = typedArray.getFloat(R.styleable.RadarView_style_ScaleXStart, 1f);
        scaleXEnd = typedArray.getFloat(R.styleable.RadarView_style_ScaleXEnd, 7f);
        scaleYStart = typedArray.getFloat(R.styleable.RadarView_style_ScaleYStart, 1f);
        scaleYEnd = typedArray.getFloat(R.styleable.RadarView_style_ScaleYEnd, 7f);

        alphaStart = typedArray.getFloat(R.styleable.RadarView_style_rippleAlphaStart, 1.0f);
        alphaEnd = typedArray.getFloat(R.styleable.RadarView_style_rippleAlphaEnd, 0f);



        //半径+线宽
        LayoutParams rippleParams = new LayoutParams((int) (2 * (rippleRadius + rippleStrokeWidth)), (int) (2 * (rippleRadius + rippleStrokeWidth)));
        rippleParams.addRule(CENTER_IN_PARENT, TRUE);
        animatorList = new ArrayList<>();
        animatorSet = new AnimatorSet();
        ripplePaint = new Paint();
        ripplePaint.setColor(rippleColor);
        if (rippleType == 1) {
            ripplePaint.setStyle(Paint.Style.FILL);
        } else {
            ripplePaint.setStyle(Paint.Style.STROKE);
            ripplePaint.setStrokeWidth(rippleStrokeWidth);
        }
        ripplePaint.setDither(true);
        ripplePaint.setAntiAlias(true);
        ripplePaint.setStrokeCap(Paint.Cap.ROUND);
        long rippleDelay = rippleDurationTime / rippleAmount;
        for (int i = 0; i < rippleAmount; i++) {
            RippleView rippleView = new RippleView(getContext());
            rippleView.setLayoutParams(rippleParams);
            addView(rippleView);
            rippleViewList.add(rippleView);
            //缩放动画
            ObjectAnimator objectAnimatorX = ObjectAnimator.ofFloat(rippleView, "ScaleX", scaleXStart, scaleXEnd);
            objectAnimatorX.setRepeatMode(ObjectAnimator.RESTART);
            objectAnimatorX.setRepeatCount(ObjectAnimator.INFINITE);
            objectAnimatorX.setStartDelay(i * rippleDelay);
            ObjectAnimator objectAnimatorY = ObjectAnimator.ofFloat(rippleView, "ScaleY", scaleYStart, scaleYEnd);
            objectAnimatorY.setRepeatMode(ObjectAnimator.RESTART);
            objectAnimatorY.setRepeatCount(ObjectAnimator.INFINITE);
            objectAnimatorY.setStartDelay(i * rippleDelay);
            //渐变动画
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(rippleView, "Alpha", alphaStart, alphaEnd);
            objectAnimator.setRepeatMode(ObjectAnimator.RESTART);
            objectAnimator.setRepeatCount(ObjectAnimator.INFINITE);
            objectAnimator.setStartDelay(i * rippleDelay);
            animatorList.add(objectAnimatorX);
            animatorList.add(objectAnimatorY);
            animatorList.add(objectAnimator);
        }

        animatorSet.setDuration(rippleDurationTime);
        Log.i(TAG, "rippleDurationTime: "+rippleDurationTime);
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorSet.playTogether(animatorList);
    }


    private class RippleView extends View {
        public RippleView(Context context) {
            super(context);
            this.setVisibility(INVISIBLE);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            int radius = Math.min(getMeasuredWidth(), getMeasuredHeight()) / 2;
            canvas.drawCircle(radius, radius, radius - rippleStrokeWidth, ripplePaint);
        }
    }

    /**
     * 开始动画
     */
    public void startRippleAnimation() {
        if (rippleAnimatorState != 1) {
            for (RippleView rippleView : rippleViewList) {
                rippleView.setVisibility(VISIBLE);
            }
            animatorSet.start();
            rippleAnimatorState = 1;
        }
    }

    /**
     * 停止动画
     */
    public void stopRippleAnimation() {
        if (rippleAnimatorState != 0) {
            animatorSet.end();
            rippleAnimatorState = 0;
        }
    }

    /**
     * 暂停动画
     */
    public void pauseRippleAnimation() {
        if (rippleAnimatorState != 2) {
            animatorSet.pause();
            rippleAnimatorState = 2;
        }
    }

    /**
     * 恢复动画
     */
    public void resumeRippleAnimation() {
        if (rippleAnimatorState == 2) {
            animatorSet.resume();
            rippleAnimatorState = 1;
        }
    }

}
