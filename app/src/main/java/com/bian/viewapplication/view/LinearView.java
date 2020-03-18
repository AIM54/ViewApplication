package com.bian.viewapplication.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.bian.viewapplication.R;

/**
 * Created by Administrator on 2018/7/14.
 */

public class LinearView extends View {
    private Paint mPaint;
    private int mHeight, mWidth;
    private Rect mRect;
    private LinearGradient linearGradient;
    private int colorsArrays[];
    private float[] rageArrays;
    private LinearLayout mLinear;


    public LinearView(Context context) {
        this(context, null);
    }

    public LinearView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mHeight = getMeasuredHeight();
        mWidth = getMeasuredWidth();
        colorsArrays = new int[]{ContextCompat.getColor(getContext(), R.color.linear_start_color), ContextCompat.getColor(getContext(), R.color.linear_end_color)};
        rageArrays = new float[]{0.7f, 0.3f};
        mRect = new Rect(0, 0, mWidth, mHeight);
        linearGradient = new LinearGradient(0, 0, 0, mHeight, colorsArrays, null, Shader.TileMode.REPEAT);
        mPaint.setShader(linearGradient);
    }

    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRect(mRect, mPaint);
    }
}
