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

import com.bian.viewapplication.R;
import com.bian.viewapplication.util.CommonLog;

/**
 * Created by Administrator on 2018/7/14.
 */

public class GradientTextView extends View {
    private TextPaint textPaint;
    private String gradientText;
    private float textSize;
    private Paint.FontMetrics fontMetrics;
    private float textHeight;
    private float textWidth;
    private int mHeight, mWidth;
    private int textStartColor, textEndColor;
    private LinearGradient mLinearGradient;

    public GradientTextView(Context context) {
        this(context, null);
    }


    public GradientTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.GradientTextView);
        gradientText = typedArray.getString(R.styleable.GradientTextView_gradient_text);
        textSize = typedArray.getDimension(R.styleable.GradientTextView_gradient_textSize, getResources().getDimension(R.dimen.sp15));
        textStartColor = typedArray.getColor(R.styleable.GradientTextView_gradient_startColor, Color.GREEN);
        textEndColor = typedArray.getColor(R.styleable.GradientTextView_gradient_endColor, Color.YELLOW);
        typedArray.recycle();
        initPaint();
    }

    private void initPaint() {
        textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextSize(textSize);
        textPaint.setTextAlign(Paint.Align.CENTER);
        fontMetrics = textPaint.getFontMetrics();
        recordLog();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        textHeight = fontMetrics.bottom - fontMetrics.top;
        textWidth = textPaint.measureText(gradientText);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int viewWidth = widthMode == MeasureSpec.EXACTLY ? widthSize : (int) textWidth;
        int viewHeight = heightMode == MeasureSpec.EXACTLY ? heightSize : (int) textHeight;
        viewWidth = Math.max(viewWidth, getSuggestedMinimumWidth());
        viewHeight = Math.max(viewHeight, getSuggestedMinimumHeight());
        setMeasuredDimension(viewWidth, viewHeight);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHeight = getMeasuredHeight();
        mWidth = getMeasuredWidth();
        mLinearGradient = new LinearGradient(0, 0, 0, mHeight, textStartColor, textEndColor, Shader.TileMode.REPEAT);
        textPaint.setShader(mLinearGradient);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawText(gradientText, 0, gradientText.length(), mWidth / 2, (mHeight - textPaint.ascent() - textPaint.descent()) / 2, textPaint);
    }

    private void recordLog() {
        CommonLog.i("fontMetrics.top:" + fontMetrics.top);
        CommonLog.i("fontMetrics.leading:" + fontMetrics.leading);
        CommonLog.i("fontMetrics.ascent:" + fontMetrics.ascent);
        CommonLog.i("fontMetrics.descent:" + fontMetrics.descent);
        CommonLog.i("fontMetrics.bottom:" + fontMetrics.bottom);
    }
}
