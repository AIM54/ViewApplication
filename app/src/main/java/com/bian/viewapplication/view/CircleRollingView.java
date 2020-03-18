package com.bian.viewapplication.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathEffect;
import android.graphics.RectF;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class CircleRollingView extends View {
    private PathEffect pathEffect;
    private Paint mRectPaint;
    private String mMessage;
    private Path mTextPath;
    private TextPaint mTextPaint;
    private RectF mRectf;


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
        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.GREEN);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setTextSize(50);
        mTextPath = new Path();
        mMessage = "常星是傻逼";
        mTextPaint.getTextPath(mMessage, 0, mMessage.length(), 0, mTextPaint.getTextSize(), mTextPath);
        pathEffect = new PathDashPathEffect(mTextPath, mTextPaint.measureText(mMessage), 40, PathDashPathEffect.Style.MORPH);
        mRectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRectPaint.setColor(Color.MAGENTA);
        mRectPaint.setStyle(Paint.Style.STROKE);
        mRectPaint.setPathEffect(pathEffect);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mRectf=new RectF(0,0,getWidth(),getHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {

    }
}
