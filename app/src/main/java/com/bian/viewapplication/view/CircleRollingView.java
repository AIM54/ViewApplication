package com.bian.viewapplication.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathEffect;
import android.graphics.RectF;
import androidx.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.bian.viewapplication.util.CommonLog;

public class CircleRollingView extends View {
    private Paint mPaint;
    private int circleRadius;
    private RectF circleRectF;
    private PathEffect pathEffect;
    private Path trianglePath;
    private Paint mRectPaint;
    private RectF mRectF;
    private TextPaint textPaint;
    public CircleRollingView(Context context) {
        this(context, null);
    }

    public CircleRollingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleRollingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        trianglePath = new Path();
        trianglePath.moveTo(0, 20);
        trianglePath.lineTo(14, 20);
        trianglePath.lineTo(7, 0);
        trianglePath.close();
        pathEffect = new PathDashPathEffect(trianglePath, 40, 40, PathDashPathEffect.Style.MORPH);
        mRectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRectPaint.setColor(Color.MAGENTA);
        mRectPaint.setStyle(Paint.Style.STROKE);
        mRectPaint.setPathEffect(pathEffect);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        CommonLog.i(getClass().getSimpleName() + ":" + isInCircleRegion(event));
        if (isInCircleRegion(event)) {
            getParent().requestDisallowInterceptTouchEvent(true);
            return true;
        }
        return super.onTouchEvent(event);

    }

    private boolean isInCircleRegion(MotionEvent event) {
        return Math.sqrt(Math.pow(event.getX() - getWidth() / 2, 2) + Math.pow(event.getY() - getHeight() / 2, 2)) < circleRadius;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        circleRadius = Math.min(getWidth(), getHeight()) / 2;
        circleRectF = new RectF(getWidth() / 2 - circleRadius, getHeight() / 2 - circleRadius, getWidth() / 2 + circleRadius, getHeight() / 2 + circleRadius);
        mRectF = new RectF(0, 0, getWidth(), getHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setColor(Color.WHITE);
        canvas.drawArc(circleRectF, 0, 120, true, mPaint);
        mPaint.setColor(Color.BLUE);
        canvas.drawArc(circleRectF, 120, 120, true, mPaint);
        mPaint.setColor(Color.RED);
        canvas.drawArc(circleRectF, 240, 120, true, mPaint);
        canvas.drawRect(mRectF, mRectPaint);
    }
}
