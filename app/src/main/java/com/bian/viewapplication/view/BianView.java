package com.bian.viewapplication.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.bian.viewapplication.R;

/**
 * Created by bian on 18-6-16.
 */

public class BianView extends View {
    private Paint mPaint;
    private int mHeight,mWidth;
    private int radius;
    private RectF arcRectF;
    public BianView(Context context) {
        super(context);
        initPaint();
    }


    public BianView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }
    private void initPaint() {
        mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(getContext().getResources().getDimension(R.dimen.dp3));
        mPaint.setColor(Color.GREEN);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mHeight=getMeasuredHeight();
        mWidth=getMeasuredWidth();
        radius=Math.min(mWidth,mHeight)/2;
        arcRectF=new RectF(0,0,radius,radius);
    }

    @Override
    protected void onDraw(Canvas canvas) {
      canvas.drawArc(arcRectF,0,120,true,mPaint);
    }
}
