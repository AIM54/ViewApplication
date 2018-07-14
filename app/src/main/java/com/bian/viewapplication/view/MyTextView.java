package com.bian.viewapplication.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.bian.viewapplication.R;

/**
 * Created by Administrator on 2018/7/9.
 */

public class MyTextView extends TextView {
    String message;
    private float textSize;
    private Paint textPaint;
    private float defaultTextSize;
    private LinearGradient mLinear;
    private int mWidth, mHeight;
    private Paint.FontMetrics fontMetrics;

    public MyTextView(Context context) {
        super(context);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        defaultTextSize = dpToPx(11);
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.MyTextView);
        message = typedArray.getString(R.styleable.MyTextView_myText);
        textSize = typedArray.getDimension(R.styleable.MyTextView_myTextSize, defaultTextSize);
        typedArray.recycle();
        initPaint();
    }


    private void initPaint() {
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextSize(textSize);
        fontMetrics = textPaint.getFontMetrics();
        mLinear = new LinearGradient(0, 0, 0, textPaint.descent() - textPaint.ascent(), Color.YELLOW, Color.RED, Shader.TileMode.MIRROR);
        textPaint.setShader(mLinear);
        textPaint.setTextAlign(Paint.Align.CENTER);
    }

    public float dpToPx(float dpValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawText(message, mWidth / 2, mHeight / 2, textPaint);
    }
}
