package com.bian.viewapplication.view;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2018/7/16.
 */

public class ShadowView extends View {
    private Paint mPaint;
    private int mHeight, mWidth;
    private int mRadius;

    public ShadowView(Context context) {
        this(context, null);
    }

    public ShadowView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
        setLayerType(LAYER_TYPE_SOFTWARE, null);
    }

    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.GREEN);
        // 设置画笔遮罩滤镜
        mPaint.setMaskFilter(new BlurMaskFilter(20, BlurMaskFilter.Blur.NORMAL));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mHeight = getMeasuredHeight();
        mWidth = getMeasuredWidth();
        mRadius = Math.min(mWidth, mHeight) / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
     canvas.drawCircle(mWidth/2,mHeight/2,mRadius,mPaint);
    }
}
