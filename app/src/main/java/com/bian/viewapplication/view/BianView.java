package com.bian.viewapplication.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.bian.viewapplication.R;

/**
 * Created by bian on 18-6-16.
 */

public class BianView extends View {
    private Paint mPaint;
    private int mHeight,mWidth;
    private int radius;
    private RectF arcRectF,tempRectF;
    private Paint linePaint;
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
        mPaint.setColor(Color.GREEN);
        linePaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setStrokeWidth(getContext().getResources().getDimension(R.dimen.dp3));
        linePaint.setColor(Color.YELLOW);
        linePaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mHeight=getMeasuredHeight();
        mWidth=getMeasuredWidth();
        radius=Math.min(mWidth,mHeight)/2;
        arcRectF=new RectF(0,0,radius,radius);
        tempRectF=new RectF(0,0,mWidth,mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
     canvas.drawCircle(100,100,100,mPaint);
     canvas.saveLayerAlpha(tempRectF,0x34,Canvas.ALL_SAVE_FLAG);
     mPaint.setColor(Color.MAGENTA);
     canvas.drawCircle(100,200,100,mPaint);
     canvas.saveLayerAlpha(tempRectF,0x66,Canvas.ALL_SAVE_FLAG);
     canvas.drawCircle(300,100,100,mPaint);
    }
}
