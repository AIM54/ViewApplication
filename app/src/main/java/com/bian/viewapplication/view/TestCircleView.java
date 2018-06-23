package com.bian.viewapplication.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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

    public TestCircleView(Context context) {
        super(context, null);
    }

    public TestCircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
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
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int circleRadius = (int) (Math.min(mWidth, mHeight) - mPaint.getStrokeWidth())/2;
        canvas.translate(mWidth / 2, mHeight / 2);
        canvas.drawCircle(0,0,circleRadius,mPaint);
    }
}
