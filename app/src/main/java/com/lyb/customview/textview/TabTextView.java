package com.lyb.customview.textview;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

/**
 * ASCustomView
 *
 * @Description： 自定义TextView 可以设置颜色 背景
 * @Date : 2020-12-02 15:35
 * @Author： Lyb
 */
public class TabTextView extends LinearLayout {
    private TextView textView;
    private int roundWidth=30;
    private int roundHeight=5;
    private int roundRadio=10;

    public TabTextView(Context context) {
        super(context);
    }


    public TabTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TabTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        removeAllViews();
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER);
        LayoutParams rippleParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textView = new TextView(getContext());
        textView.setLayoutParams(rippleParams);
        textView.setText("背包");
        textView.setTextSize(16);
        addView(textView);
        RoundRectView roundRectView = new RoundRectView(getContext(), roundRadio, roundRadio);
        LayoutParams roundRectViewParams = new LayoutParams(dip2px(getContext(), roundWidth), dip2px(getContext(), roundHeight));
        roundRectViewParams.gravity = Gravity.BOTTOM|Gravity.CENTER;
        roundRectView.setLayoutParams(roundRectViewParams);
        addView(roundRectView);
    }

    public TextView getTextView() {
        return textView;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
        invalidate();
    }

    public int getRoundWidth() {
        return roundWidth;
    }

    public void setRoundWidth(int roundWidth) {
        this.roundWidth = roundWidth;
        invalidate();
    }

    public int getRoundHeight() {
        return roundHeight;
    }

    public void setRoundHeight(int roundHeight) {
        this.roundHeight = roundHeight;
        invalidate();
    }

    public int getRoundRadio() {
        return roundRadio;
    }

    public void setRoundRadio(int roundRadio) {
        this.roundRadio = roundRadio;
        invalidate();
    }

    /**
     * 根据手机的分辨率从 dp(像素) 的单位 转成为 px
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }
}
