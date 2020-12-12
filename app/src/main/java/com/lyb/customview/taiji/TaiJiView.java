package com.lyb.customview.taiji;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.lyb.customview.R;

import androidx.annotation.IntRange;
import androidx.annotation.Nullable;

import static android.animation.ValueAnimator.INFINITE;

/**
 * ASCustomView
 *
 * @Description：
 * @Date : 2020-12-11 13:40
 * @Author： Lyb
 */
public class TaiJiView extends View {
    private Paint leftPaint;
    private Paint rightPaint;
    private int radius;
    private ObjectAnimator objectAnimator;//属性动画
    private int animaltime = 10000;//动画的旋转时间（速度）
    private int leftColor = Color.BLACK;
    private int rightColor = Color.WHITE;

    public TaiJiView(Context context) {
        this(context, null);
    }

    public TaiJiView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TaiJiView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TaiJiView);
        leftColor = typedArray.getColor(R.styleable.TaiJiView_LeftColor, Color.BLACK);
        rightColor = typedArray.getColor(R.styleable.TaiJiView_RightColor, Color.WHITE);
        animaltime = typedArray.getInteger(R.styleable.TaiJiView_AnimalTime, 10000);
        initPaint();
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        leftPaint = new Paint();
        leftPaint.setStyle(Paint.Style.FILL);
        leftPaint.setAntiAlias(true);
        leftPaint.setColor(leftColor);
        rightPaint = new Paint();
        rightPaint.setStyle(Paint.Style.FILL);
        rightPaint.setAntiAlias(true);
        rightPaint.setColor(rightColor);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        radius = Math.min(getMeasuredWidth(), getMeasuredHeight()) / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //两个半圆
        RectF rectF = new RectF(0, 0, radius * 2, radius * 2);
        canvas.drawArc(rectF, 90, 180, true, leftPaint);
        canvas.drawArc(rectF, -90, 180, true, rightPaint);
        //上半部圆
        RectF rectTopF = new RectF((radius / 2) - 2, 0, radius + (radius / 2), radius);
        canvas.drawArc(rectTopF, -90, 180, true, leftPaint);
        //下半部圆
        RectF rectBottomF = new RectF((radius / 2) + 2, radius, radius + (radius / 2), radius * 2);
        canvas.drawArc(rectBottomF, 90, 180, true, rightPaint);
        //上 完整圆
        int tcx = radius;
        int tcy = radius / 2;
        canvas.drawCircle(tcx, tcy, radius / 8, rightPaint);
        //下 完整圆
        int bcx = radius;
        int bcy = radius + radius / 2;
        canvas.drawCircle(bcx, bcy, radius / 8, leftPaint);
        super.onDraw(canvas);
        //        startRotation();
    }

    public void startRotation() {
        if (objectAnimator == null) {
            objectAnimator = ObjectAnimator.ofFloat(this, "rotation", 0f, 360);
            objectAnimator.setDuration(animaltime);
            objectAnimator.setInterpolator(new LinearInterpolator());
            objectAnimator.setRepeatCount(INFINITE);
            objectAnimator.setRepeatMode(ValueAnimator.RESTART);
            objectAnimator.start();
        }
    }

    public void startRotation(@IntRange(from = 1) int animaltime) {
        this.animaltime = animaltime;
    }

    /**
     * 停止旋转
     */
    public void pauseRotation() {
        if (objectAnimator != null && objectAnimator.isRunning()) {
            objectAnimator.pause();
        }
    }

    /**
     * 继续旋转
     */
    public void reStartRotation() {
        if (objectAnimator != null) {
            objectAnimator.resume();
        }
    }

    /**
     * 停止旋转
     */
    public void stopRotation() {
        if (objectAnimator != null) {
            objectAnimator.cancel();
            objectAnimator = null;
        }
    }
}
