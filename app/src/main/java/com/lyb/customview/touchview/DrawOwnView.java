package com.lyb.customview.touchview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * ASCustomView
 *
 * @Description：
 * @Date : 2020-11-26 16:44
 * @Author： Lyb
 */
public class DrawOwnView extends View {
    private static final String TAG = "DrawOwnView";

    private Paint paint;
    private float centerX = 0;
    private float centerY = 0;

    public DrawOwnView(Context context) {
        this(context, null);
    }

    public DrawOwnView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawOwnView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        paint = new Paint();
        paint.setStrokeWidth(5);
        paint.setColor(Color.RED);
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStyle(Paint.Style.FILL);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        centerX = (getMeasuredWidth() - getPaddingLeft() - getPaddingRight()) / 2;
        centerY = (getMeasuredHeight() - getPaddingTop() - getPaddingBottom()) / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(centerX, centerY, 50, paint);
    }

    public void setCenterXY(float centerX, float centerY) {
        this.centerX = centerX;
        this.centerY = centerY;
        invalidate();
    }

}
