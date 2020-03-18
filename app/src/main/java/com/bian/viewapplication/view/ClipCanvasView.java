package com.bian.viewapplication.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * Created by Administrator on 2018/7/17.
 */

public class ClipCanvasView extends View {
    private Paint mPaint;
    private int mHeight, mWidth, mRadius;
    private RectF viewRectF;
    private Path clipPath;

    public ClipCanvasView(Context context) {
        this(context, null);
    }

    public ClipCanvasView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.GREEN);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mHeight = getMeasuredHeight();
        mWidth = getMeasuredWidth();
        viewRectF = new RectF(0, 0, mWidth, mHeight);
        mRadius = Math.min(mWidth, mHeight) / 2;
        clipPath = new Path();
        int tempRectFHeight = 300;
        RectF tempRectF = new RectF(0, mHeight / 2 - tempRectFHeight / 2, mWidth, mHeight / 2 + tempRectFHeight / 2);
        clipPath.addRoundRect(tempRectF, 30, 30, Path.Direction.CW);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.saveLayer(viewRectF, mPaint, Canvas.ALL_SAVE_FLAG);
        canvas.clipPath(clipPath, Region.Op.REPLACE);
        canvas.drawCircle(mWidth / 2, mHeight / 2, mRadius, mPaint);
        canvas.restore();
    }
}
