package com.lyb.customview.textview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;

/**
 * ASCustomView
 *
 * @Description： 圆角矩形
 * @Date : 2020-12-02 15:39
 * @Author： Lyb
 */
public class RoundRectView extends View {
    private Paint paint;
    private RectF rectF;
    private int xRadio=10;//X角度
    private int yRadio=10;//y角度

    public RoundRectView(Context context,int xRadio,int yRadio) {
        super(context);
        this.xRadio=xRadio;
        this.yRadio=yRadio;
        initPaint();
    }
    private void initPaint() {
        paint=new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        rectF=new RectF();
        rectF.set(getPaddingLeft(),getPaddingTop(),getPaddingLeft()+widthSize,getPaddingTop()+heightSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRoundRect(getPaddingLeft(),getPaddingTop(),getPaddingLeft()+getMeasuredWidth(),getPaddingTop()+getMeasuredHeight(),dip2px(getContext(),xRadio),dip2px(getContext(),xRadio),paint);
    }
    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
    /**
     * 根据手机的分辨率从 dp(像素) 的单位 转成为 px
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
