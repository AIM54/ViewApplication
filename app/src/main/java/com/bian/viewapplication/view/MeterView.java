package com.bian.viewapplication.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import androidx.annotation.Nullable;

import com.bian.viewapplication.util.CommonLog;

public class MeterView extends View implements ScaleGestureDetector.OnScaleGestureListener {
    private Paint mPaint;
    private Path mPath1, mPath2;
    private Point mPoint;
    private int radius;
    private ScaleGestureDetector scaleGestureDetector;
    public MeterView(Context context) {
        this(context, null);
    }

    public MeterView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MeterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
        initScaleDesture();

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return super.dispatchTouchEvent(event);
    }

    private void initScaleDesture() {
        scaleGestureDetector=new ScaleGestureDetector(getContext(),this);
    }

    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.GREEN);
        mPaint.setStrokeWidth(20);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
       return scaleGestureDetector.onTouchEvent(event);
    }

    private void getFingerIndexInView(MotionEvent event) {
        int fingerCount = event.getPointerCount();
        for (int index = 0; index < fingerCount; index++) {
            CommonLog.i("第" + index + "根手指的坐标:" + event.getX(index) + "||" + event.getY(index));
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mPath1 = new Path();
        mPath1.moveTo(getWidth() / 2, 0);
        mPath1.lineTo(getWidth() / 2, getHeight());
        mPath2 = new Path();
        mPath2.moveTo(0, getHeight() / 2);
        mPath2.lineTo(getWidth(), getHeight() / 2);
        radius = 10;
        mPoint = new Point();
        mPoint.x = getWidth() / 2;
        mPoint.y = getHeight() / 2;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        CommonLog.i("onMeasure("+widthMeasureSpec+","+heightMeasureSpec+")");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        CommonLog.i("onDraw");
        canvas.drawPath(mPath1, mPaint);
        mPaint.setColor(Color.YELLOW);
        canvas.drawPath(mPath2, mPaint);
        canvas.drawCircle(mPoint.x, mPoint.y, radius, mPaint);
    }


    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        CommonLog.i("onScale:"+detector.getCurrentSpanX());
        detector.getFocusY();
        detector.getFocusX();
        return true;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        CommonLog.i("onScaleBegin:"+detector.getCurrentSpanX());
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {
        CommonLog.i("onScaleEnd:"+detector.getCurrentSpanX());
    }
}
