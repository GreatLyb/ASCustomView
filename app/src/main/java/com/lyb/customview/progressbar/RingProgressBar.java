package com.lyb.customview.progressbar;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;

import com.lyb.customview.R;

import androidx.annotation.ColorRes;
import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

/**
 * ASCustomView
 *
 * @Description： 自定义的圆环进度条
 * 1.可以自定义圆环的宽高 背景色 进度色
 * 2.支持中间文本的显示
 * 3.支持动画设置进度，支持自定义设置动画的插值器
 * @Date : 2020-11-26 9:32
 * @Author： Lyb
 */
public class RingProgressBar extends View {
    private static final String TAG = "RingProgressBar";
    private Paint backgroundPaint;//背景画笔
    private Paint progressPaint;//进度画笔
    private TextPaint textPaint;//中间的文字画笔
    private float progress;//当前进度
    private RectF mRectF;//绘制区域
    /**
     * 动画总时长
     */
    private long durationTime = 2000;
    //是否显示文本
    private boolean isShowText;
    //是否开启动画
    private boolean EnableAnimation;

    private Interpolator interpolator;//动画的插值器 默认减速插值器  DecelerateInterpolator


    //new的时候调用
    public RingProgressBar(Context context) {
        this(context, null);
    }

    //在xml中使用
    public RingProgressBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    //如果这个view 有了style 在这使用
    public RingProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        @SuppressLint({"Recycle", "CustomViewStyleable"})
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RingProgressBar_style);
        float backgroundWidth = typedArray.getDimension(R.styleable.RingProgressBar_style_barBackgroundWidth, 5);
        int backgroundColor = typedArray.getColor(R.styleable.RingProgressBar_style_barBackgroundColor, Color.LTGRAY);
        float barWidth = typedArray.getDimension(R.styleable.RingProgressBar_style_barProgressWidth, 5);
        int barColor = typedArray.getColor(R.styleable.RingProgressBar_style_barColor, Color.BLUE);
        int textColor = typedArray.getColor(R.styleable.RingProgressBar_style_progress_text_color, Color.BLACK);
        float textSize = typedArray.getDimension(R.styleable.RingProgressBar_style_progress_text_size, 16);
        progress = typedArray.getFloat(R.styleable.RingProgressBar_style_progress, 0);
        isShowText = typedArray.getBoolean(R.styleable.RingProgressBar_style_isShowText, false);
        EnableAnimation = typedArray.getBoolean(R.styleable.RingProgressBar_style_EnableAnimation, false);
        durationTime = typedArray.getInteger(R.styleable.RingProgressBar_style_durationTime, 2000);
        // 初始化背景圆环画笔
        backgroundPaint = initPaint(backgroundWidth, backgroundColor);
        // 初始化进度圆环画笔
        progressPaint = initPaint(barWidth, barColor);
        // 初始化TestPaint
        textPaint = new TextPaint();
        textPaint.setColor(textColor);
        textPaint.setTextSize(textSize);
        textPaint.setAntiAlias(true);
        textPaint.setTextAlign(Paint.Align.CENTER);
        //获取当前进度
        typedArray.recycle();
    }

    /**
     * 初始化画笔
     */
    private Paint initPaint(float width, int color) {
        Paint paint = new Paint();
        paint.setStrokeWidth(width);
        paint.setColor(color);
        paint.setAntiAlias(true); //抗锯齿
        paint.setStyle(Paint.Style.STROKE); //充满
        paint.setStrokeCap(Paint.Cap.ROUND);//圆角
        paint.setDither(true);                 // 设置抖动
        return paint;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //计算大小
        int widthSize = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        int heightSize = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();
        //矩形的宽度
        int mRectLength = (int) (Math.max(widthSize, heightSize) - Math.max(backgroundPaint.getStrokeWidth(), progressPaint.getStrokeWidth()));
        //左顶点的坐标
        int leftPoint = getPaddingLeft() + (widthSize - mRectLength) / 2;
        //左上角
        int rightPoint = getPaddingTop() + (widthSize - mRectLength) / 2;
        mRectF = new RectF(leftPoint, rightPoint, leftPoint + mRectLength, rightPoint + mRectLength);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.i(TAG, "onDraw: ");
        //绘制背景
        canvas.drawArc(mRectF, 0, 360, false, backgroundPaint);
        if (progress > 100) {
            progress = 100;
        }
        if (isShowText) {
            //绘制文字
            String progressText = (int) progress + "%";
            Rect bounds = new Rect();
            textPaint.getTextBounds(progressText, 0, progressText.length(), bounds);
            //计算出文字y轴的偏移量。 文字整体的高度除以2
            float offset = (bounds.top + bounds.bottom) / 2;
            canvas.drawText(progressText, mRectF.centerX(), mRectF.centerY() - offset, textPaint);
        }
        //绘制进度
        canvas.drawArc(mRectF, 270, 360 * progress / 100, false, progressPaint);
    }

    /**
     * 设置进度
     */
    public void setProgress(@FloatRange(from = 0, to = 100) float progress1) {
        float lastProgress = progress;
        this.progress = progress1;
        if (EnableAnimation) {
            ValueAnimator valueAnimator = ValueAnimator.ofFloat(lastProgress, progress);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    progress = (float) animation.getAnimatedValue();
                    Log.i(TAG, "onAnimationUpdate: " + progress);
                    invalidate();
                }
            });
            valueAnimator.setInterpolator(interpolator);
            valueAnimator.setDuration(durationTime);
            //        valueAnimator.setRepeatMode(REVERSE);
            //        valueAnimator.setRepeatCount(10);
            valueAnimator.start();
        } else {
            invalidate();
        }
    }


    /**
     * 设置动画的插值器
     *
     * @param interpolator 插值器
     */
    public void setInterpolator(@NonNull Interpolator interpolator) {
        this.interpolator = interpolator;
        if (EnableAnimation) {
            setProgress(progress);
        }
    }

    public Interpolator getInterpolator() {
        if (interpolator == null) {
            interpolator = new DecelerateInterpolator();
        }
        return interpolator;
    }

    /**
     * 设置进度条的颜色
     *
     * @param color 颜色
     */
    public void setProgressBarColor(@ColorRes int color) {
        progressPaint.setColor(ContextCompat.getColor(getContext(), color));
        invalidate();
    }

    /**
     * 设置进度条的宽度
     *
     * @param width 宽度
     */
    public void setProgressBarWidth(float width) {
        progressPaint.setStrokeWidth(width);
        invalidate();
    }

    /**
     * 设置进度条的背景色
     *
     * @param color 颜色
     */
    public void setBackGroundBarColor(@ColorRes int color) {
        backgroundPaint.setColor(ContextCompat.getColor(getContext(), color));
        invalidate();
    }

    /**
     * 设置进度条背景的宽度
     *
     * @param width 宽度
     */
    public void setProgressBarBackgroundWidth(float width) {
        backgroundPaint.setStrokeWidth(width);
        invalidate();
    }

    /**
     * 设置进度条背景的宽度
     *
     * @param durationTime 宽度
     */
    public void setDurationTime(long durationTime) {
        this.durationTime = durationTime;
    }


    public float getProgress() {
        return progress;
    }
}
