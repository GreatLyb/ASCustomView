package com.lyb.customview.RadarView;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
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
public class RadarViewPro extends RelativeLayout {
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
    private int rippleColor;//0 未开始  1.进行中 2 暂停状态

    /**
     * 放大的倍数 默认放大3倍
     */
    private @FloatRange(from = 1)
    float ScaleMultiple = 3;

    private @FloatRange(from = 0)
    float alphaStart = 1.0f;
    private @FloatRange(from = 0)
    float alphaEnd = 0f;
    private RippleView rippleView;
    private float diameter;//直径


    public RadarViewPro(Context context) {
        this(context, null);
    }

    public RadarViewPro(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RadarViewPro(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RadarViewPro_style);
        rippleDurationTime = typedArray.getInteger(R.styleable.RadarViewPro_style_rippleDurationTimePro, 3000);
        rippleAmount = typedArray.getInteger(R.styleable.RadarViewPro_style_rippleAmountPro, 4);
        rippleType = typedArray.getInteger(R.styleable.RadarViewPro_style_rippleTypePro, 1);
        rippleRadius = typedArray.getFloat(R.styleable.RadarViewPro_style_rippleRadiusPro, 50);
        rippleColor = typedArray.getColor(R.styleable.RadarViewPro_style_rippleColorPro, Color.RED);

        alphaStart = typedArray.getFloat(R.styleable.RadarViewPro_style_rippleAlphaStartPro, 1.0f);
        alphaEnd = typedArray.getFloat(R.styleable.RadarViewPro_style_rippleAlphaEndPro, 0f);
        ScaleMultiple = typedArray.getFloat(R.styleable.RadarViewPro_style_ScaleMultiple, 3f);


        //半径+线宽
        diameter = 2 * (rippleRadius + rippleStrokeWidth);
        LayoutParams rippleParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
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
        int delay = rippleDurationTime / rippleAmount;
        ripplePaint.setStrokeCap(Paint.Cap.ROUND);
        for (int i = 0; i < rippleAmount; i++) {
            rippleView = new RippleView(getContext());
            rippleView.setLayoutParams(rippleParams);
            addView(rippleView);
            rippleViewList.add(rippleView);
            ObjectAnimator objectAnimator = rippleView.getObjectAnimator();
            objectAnimator.setStartDelay(i * delay);
            animatorList.add(objectAnimator);
        }
        animatorSet.setDuration(rippleDurationTime);
        Log.i(TAG, "rippleDurationTime: " + rippleDurationTime);
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorSet.playTogether(animatorList);
    }


    private class RippleView extends View {
        float mRippleRadius;
        private ObjectAnimator objectAnimator;
        private Paint paint;
        private int dx;//圆心 X 坐标
        private int dy;//圆心 Y 坐标

        public RippleView(Context context) {
            super(context);
            mRippleRadius = diameter / 2;
            this.setVisibility(INVISIBLE);
            paint = new Paint();
            paint.setColor(Color.RED);
            paint.setStyle(Paint.Style.FILL);
            paint.setAntiAlias(true);
            paint.setStrokeWidth(2);
            paint.setStrokeCap(Paint.Cap.ROUND);
            objectAnimator = ObjectAnimator.ofFloat(this, "Alpha", alphaStart, alphaEnd);
            objectAnimator.setRepeatMode(ObjectAnimator.RESTART);
            objectAnimator.setRepeatCount(ObjectAnimator.INFINITE);
            objectAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                    RippleView.this.setVisibility(VISIBLE);
                }
            });
            objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float rate = (float) animation.getAnimatedValue();
                    float total = diameter * ScaleMultiple;//总量
                    //  1->0 对应 1-ScaleMultiple
                    mRippleRadius = (1 - rate) * total;
                    invalidate();
                }
            });
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            dx = getMeasuredWidth() / 2;
            dy = getMeasuredHeight() / 2;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            canvas.drawCircle(dx, dy, mRippleRadius - rippleStrokeWidth, ripplePaint);
            //            canvas.drawLine(0,dy,getMeasuredWidth(),dy,paint);
            //            canvas.drawLine(dx,0,dx,getMeasuredHeight(),paint);
            //            canvas.drawPoint(dx,dy,paint);
        }

        public ObjectAnimator getObjectAnimator() {
            return objectAnimator;
        }
    }

    /**
     * 开始动画
     */
    public void startRippleAnimation() {
        if (rippleAnimatorState != 1) {
            //            for (RippleView rippleView : rippleViewList) {
            //                rippleView.setVisibility(VISIBLE);
            //            }
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
