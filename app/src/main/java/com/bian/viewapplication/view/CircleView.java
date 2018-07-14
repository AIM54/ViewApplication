package com.bian.viewapplication.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.bian.viewapplication.util.CommonLog;

/**
 * Created by Administrator on 2018/7/14.
 */

public class CircleView extends View {
    private Paint mPaint;
    private SweepGradient mSweepGradient;
    private int mHeight, mWidth, mRadius;
    private float sweepDegree;
    private ValueAnimator valueAnimator;

    public CircleView(Context context) {
        this(context, null);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        sweepDegree = 0;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mHeight = getMeasuredHeight();
        mWidth = getMeasuredWidth();
        mRadius = Math.min(mWidth, mHeight) / 2;
        mSweepGradient = new SweepGradient(mWidth / 2, mHeight / 2, Color.GREEN, Color.TRANSPARENT);
        mPaint.setShader(mSweepGradient);
        beginScan();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.rotate(sweepDegree, mWidth / 2, mHeight / 2);
        canvas.drawCircle(mWidth / 2, mHeight / 2, mRadius, mPaint);
    }

    private void beginScan() {
        valueAnimator = ValueAnimator.ofFloat(360, 0);
        valueAnimator.addUpdateListener(animation -> {
            sweepDegree = (float) animation.getAnimatedValue();
            invalidate();
        });
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setDuration(1000);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (valueAnimator != null && valueAnimator.isRunning()) {
            valueAnimator.cancel();
            valueAnimator = null;
        }
    }
}



