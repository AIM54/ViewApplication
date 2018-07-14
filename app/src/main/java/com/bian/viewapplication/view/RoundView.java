package com.bian.viewapplication.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.bian.viewapplication.R;

/**
 * Created by Administrator on 2018/7/14.
 */

public class RoundView extends View {
    private Paint mPaint;
    private RectF mRectF;
    private float mRadius;
    private int mHeight, mWidth;
    private int diffWidth;
    private ValueAnimator valueAnimator;

    public RoundView(Context context) {
        this(context, null);
    }

    public RoundView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        mRadius = Math.min(mWidth, mHeight) / 4;
        mRectF = new RectF();
        beginAnimal((int) ((mWidth - mRadius) / 2));
    }


    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(ContextCompat.getColor(getContext(), R.color.orange));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = (int) (mRadius + diffWidth);
        mRectF.set(-width, -mRadius, width, mRadius);
        canvas.translate(mWidth / 2, mHeight / 2);
        canvas.drawRoundRect(mRectF, mRadius, mRadius, mPaint);
    }


    private void beginAnimal(int value) {
        valueAnimator = ValueAnimator.ofInt(0, value);
        valueAnimator.addUpdateListener(animation -> {
            diffWidth = (int) animation.getAnimatedValue();
            invalidate();
        });
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setDuration(2000);
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
