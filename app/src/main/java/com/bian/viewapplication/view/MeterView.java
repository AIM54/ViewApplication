package com.bian.viewapplication.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import com.bian.viewapplication.util.CommonLog;

public class MeterView extends View implements ScaleGestureDetector.OnScaleGestureListener {
    private Paint mPaint;
    private Path mPath1, mPath2;
    private ScaleGestureDetector mScaleGestureDetector;
    private Point mPoint;
    private int radius;

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

    private void initScaleDesture() {

    }

    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.GREEN);
        mPaint.setStrokeWidth(20);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        CommonLog.i("MotionEvent.getAction:"+event.getAction());
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                mPoint.x= (int) event.getX();
                mPoint.y= (int) event.getY();
                invalidate();
                break;
            case MotionEvent.ACTION_SCROLL:
                CommonLog.i("ACTION_SCROLL_PointerCount:"+event.getX());
                break;
        }
        return true;
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
        radius=10;
        mPoint=new Point();
        mPoint.x=getWidth()/2;
        mPoint.y=getHeight()/2;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(mPath1, mPaint);
        mPaint.setColor(Color.YELLOW);
        canvas.drawPath(mPath2, mPaint);
        canvas.drawCircle(mPoint.x,mPoint.y,radius,mPaint);
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
