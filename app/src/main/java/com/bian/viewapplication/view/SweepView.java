package com.bian.viewapplication.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.bian.viewapplication.R;

/**
 * Created by Administrator on 2018/7/14.
 */

public class SweepView extends View {
    private ValueAnimator valueAnimator;
    private Paint mPaint;
    private int mHeight, mWidth;
    private RectF mRectF;
    private int mSmallLength;
    private SweepGradient sweepGradient;
    private int colorArrays[];
    private float angleArrays[];

    public SweepView(Context context) {
        super(context);
    }

    public SweepView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(ContextCompat.getColor(getContext(), R.color.orange));
        mRectF = new RectF();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        mSmallLength = Math.min(mWidth, mHeight) / 2;
        colorArrays = new int[]{Color.GREEN, Color.TRANSPARENT};
        angleArrays = new float[]{0, 30};
        sweepGradient = new SweepGradient(0, 0, colorArrays, angleArrays);
        mPaint.setShader(sweepGradient);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mRectF.set(-mSmallLength, -mSmallLength, mSmallLength, mSmallLength);
        canvas.translate(mWidth / 2, mHeight / 2);
        canvas.drawArc(mRectF, 0, 30, true, mPaint);
    }

    private void beginAnimal(int value) {
        valueAnimator = ValueAnimator.ofInt(0, value);
        valueAnimator.addUpdateListener(animation -> {
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
