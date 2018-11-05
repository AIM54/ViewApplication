package com.bian.viewapplication.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.bian.viewapplication.R;

/**
 * Created by bian on 2018/6/23.
 */

public class TestCircleView extends View {
    private Paint mPaint;
    private int mWidth, mHeight;
    private float cosValue = 0.5f;
    private Path trianglePath;

    public TestCircleView(Context context) {
        super(context, null);
    }

    public TestCircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.GREEN);
        mPaint.setStrokeWidth(getResources().getDimension(R.dimen.dp3));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        trianglePath = new Path();
        beginAnimal();
    }

    private void beginAnimal() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.setDuration(2000);
        valueAnimator.addUpdateListener(animation -> {
            cosValue = (float) animation.getAnimatedValue();
            invalidate();
        });
        valueAnimator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int circleRadius = (int) (Math.min(mWidth, mHeight) - mPaint.getStrokeWidth()) / 2;
        canvas.translate(mWidth / 2, mHeight / 2);
        canvas.drawCircle(0, 0, circleRadius, mPaint);
        canvas.drawPoint(0, 0, mPaint);
        int rightX = (int) (circleRadius * Math.sin(Math.acos(cosValue)));
        trianglePath.reset();
        trianglePath.lineTo(-rightX, cosValue * circleRadius);
        trianglePath.lineTo(rightX, cosValue * circleRadius);
        trianglePath.close();
        canvas.drawPath(trianglePath,mPaint);
    }
}
