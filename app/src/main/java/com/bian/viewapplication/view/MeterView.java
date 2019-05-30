package com.bian.viewapplication.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;

import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewConfiguration;

import com.bian.viewapplication.util.CommonLog;

import java.util.TreeSet;
import java.util.concurrent.locks.ReentrantLock;

public class MeterView extends View implements ScaleGestureDetector.OnScaleGestureListener {
    private Paint mPaint;
    private Path mPath1, mPath2;
    private ScaleGestureDetector mScaleGestureDetector;
    private Point mPoint;
    private int radius;
    private ReentrantLock mReentrantLock;
    private ViewConfiguration mViewConfiguration;

    private TreeSet<String> treeSet;
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
        mReentrantLock = new ReentrantLock();
        mReentrantLock.lock();
    }

    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.GREEN);
        mPaint.setStrokeWidth(20);
        mViewConfiguration=ViewConfiguration.get(getContext());
        mViewConfiguration.getScaledEdgeSlop();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
       CommonLog.i("event.Pressure:"+event.getPressure());
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_POINTER_DOWN:
                CommonLog.i("ACTION_POINTER_DOWN:" + event.getActionIndex());
                event.getEdgeFlags();
                break;
            case MotionEvent.ACTION_POINTER_UP:
                CommonLog.i("ACTION_POINTER_UP:" + event.getActionIndex());
                break;
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                mPoint.x = (int) event.getX();
                mPoint.y = (int) event.getY();
                getFingerIndexInView(event);
                invalidate();
                break;
            case MotionEvent.ACTION_SCROLL:
                CommonLog.i("ACTION_SCROLL_PointerCount:" + event.getX());
                break;

        }
        return true;
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
        return true;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        return false;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {

    }
}
