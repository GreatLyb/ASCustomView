package com.lyb.customview.RadarView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * ASCustomView
 *
 * @Description：
 * @Date : 2020-12-03 16:31
 * @Author： Lyb
 */
public class RadarScanningView extends View {
    private static final int DEFAULT_RADAR_SCAN_ALPHA = 0x99;

    /**
     * 网格画笔
     */
    private Paint gridPaint;
    /**
     * 背景画笔
     */
    private Paint bgPaint;
    /**
     * 扫描画笔
     */
    private Paint scanPaint;
    private int radius = 350;
    private int circleCount = 3;
    private Matrix matrix;
    private boolean threadRunning = true;
    private boolean isstart = true;
    //设定雷达扫描方向
    private int direction = 1;
    //旋转效果起始角度
    private int start = 0;
    private ScanThread mThread;

    public RadarScanningView(Context context) {
        this(context, null);
    }

    public RadarScanningView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RadarScanningView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setBackgroundColor(Color.TRANSPARENT);
        gridPaint = initPaint();
        bgPaint = initPaint();
        //        scanPaint=initPaint();
        gridPaint.setStrokeWidth(2);
        gridPaint.setStyle(Paint.Style.STROKE);
        gridPaint.setColor(Color.WHITE);
        bgPaint.setStyle(Paint.Style.FILL);
        bgPaint.setColor(0x99000000);
        //        scanPaint.setColor(Color.BLUE);
        //暗绿色的画笔
        scanPaint = new Paint();
        scanPaint.setColor(0x9D00ff00);
        scanPaint.setAntiAlias(true);
        Shader mShader = new SweepGradient(radius, radius, Color.TRANSPARENT, Color.BLUE);
        scanPaint.setShader(mShader);

        //        Shader sweepGradient=new SweepGradient(radius,radius, Color.TRANSPARENT, Color.GREEN);
        //        scanPaint.setShader(sweepGradient);

    }

    private Paint initPaint() {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeCap(Paint.Cap.ROUND);
        return paint;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //        int bgWidth = getMeasuredWidth();
        //        int bgHeight = getMeasuredHeight();
        //        radius = Math.min(bgWidth, bgHeight)/2;
        setMeasuredDimension(radius * 2, radius * 2);
        Log.i("Lyb", "radius=" + radius);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        setBackgroundColor(Color.TRANSPARENT);
        Log.i("Lyb", "onDraw=" + radius);
        canvas.drawCircle(radius, radius, radius, bgPaint);
        for (int i = 0; i < circleCount; i++) {
            canvas.drawCircle(radius, radius, radius / circleCount * i, gridPaint);
        }
        canvas.drawLine(getPaddingLeft(), radius, getPaddingLeft() + getMeasuredWidth(), radius, gridPaint);
        canvas.drawLine(radius, getPaddingTop(), radius, getPaddingTop() + getMeasuredHeight(), gridPaint);
        canvas.concat(matrix);
        canvas.drawCircle(radius, radius, 350, scanPaint);
        super.onDraw(canvas);

    }

    public void start() {
        mThread = new ScanThread(this);
        mThread.setName("radar");
        mThread.start();
        threadRunning = true;
        isstart = true;
    }

    public void stop() {
        if (mThread!=null){
            mThread.stop();
            mThread=null;
        }
        threadRunning = false;
        isstart = false;
    }

    protected class ScanThread extends Thread {

        private RadarScanningView view;

        public ScanThread(RadarScanningView view) {
            this.view = view;
        }

        @Override
        public void run() {
            // TODO Auto-generated method stub
            while (threadRunning) {
                if (isstart) {
                    view.post(new Runnable() {
                        @Override
                        public void run() {
                            start = start + 1;
                            matrix = new Matrix();
                            //设定旋转角度,制定进行转转操作的圆心
                            //                            matrix.postRotate(start, viewSize / 2, viewSize / 2);
                            //                            matrix.setRotate(start,viewSize/2,viewSize/2);
                            matrix.preRotate(direction * start, radius, radius);
                            view.invalidate();
                        }
                    });
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
