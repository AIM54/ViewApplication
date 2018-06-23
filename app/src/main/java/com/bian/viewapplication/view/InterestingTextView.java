package com.bian.viewapplication.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.bian.viewapplication.R;
import com.bian.viewapplication.util.CommonLog;


/**
 * Created by bianmingliang on 2018/6/23.
 */

public class InterestingTextView extends View {
    private TextPaint mTextPaint;
    private Paint mPaint;
    private int textColor;
    private float textSize;
    private String message;
    private Path tempTextPath, animalTextPath;
    private TextView textView;
    private PathMeasure textPathMeasure;
    private float currentPostion;
    private long animalDuration;
    private Paint.FontMetrics fontMetrics;
    private int mHeight, mWidth;

    public InterestingTextView(Context context) {
        this(context, null);
    }

    public InterestingTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttrs(attrs);
        initPaint();
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.InterestingTextView);
        textColor = typedArray.getColor(R.styleable.InterestingTextView_textColor, Color.GREEN);
        textSize = typedArray.getDimension(R.styleable.InterestingTextView_textSize, getResources().getDimension(R.dimen.sp20));
        message = typedArray.getString(R.styleable.InterestingTextView_text);
        animalDuration = typedArray.getInteger(R.styleable.InterestingTextView_duration, 1000);
        typedArray.recycle();
    }

    private void initPaint() {
        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(textColor);
        mTextPaint.setTextSize(textSize);
        fontMetrics = mTextPaint.getFontMetrics();
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.GREEN);
        mPaint.setStrokeWidth(getResources().getDimension(R.dimen.dp3));
        CommonLog.i(mTextPaint.ascent() + "" + mTextPaint.descent());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = 0;
        int width = 0;
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (heightMode == MeasureSpec.EXACTLY && widthMode == MeasureSpec.EXACTLY) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        } else {
            if (widthMode == MeasureSpec.EXACTLY) {
                width = widthSize;
            } else if ((widthMode & (MeasureSpec.AT_MOST | MeasureSpec.UNSPECIFIED)) != 0) {
                width = (int) mTextPaint.measureText(message) + getPaddingLeft() + getPaddingRight();
            }
            if (heightMode == MeasureSpec.EXACTLY) {
                height = heightSize;
            } else if ((heightMode & (MeasureSpec.AT_MOST | MeasureSpec.UNSPECIFIED)) != 0) {
                height = (int) (fontMetrics.bottom - fontMetrics.top + fontMetrics.leading + getPaddingTop() + getPaddingBottom());
            }
        }
        height = Math.max(height, getSuggestedMinimumHeight());
        width = Math.max(width, getSuggestedMinimumWidth());
        setMeasuredDimension(width, height);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mHeight = getMeasuredHeight();
        mWidth = getMeasuredWidth();
        tempTextPath = new Path();
        animalTextPath = new Path();
        mTextPaint.getTextPath(message, 0, message.length(), mWidth / 2, (mHeight - mTextPaint.ascent() - mTextPaint.descent()) / 2, tempTextPath);
        textPathMeasure = new PathMeasure(tempTextPath, false);
        float totalPathLength = textPathMeasure.getLength();
        while (textPathMeasure.nextContour()) {
            totalPathLength += textPathMeasure.getLength();
        }
        beginTextAnimal(totalPathLength);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        animalTextPath.reset();
        textPathMeasure.setPath(tempTextPath, false);
        while (currentPostion > textPathMeasure.getLength()) {
            textPathMeasure.getSegment(0, textPathMeasure.getLength(), animalTextPath, true);
            currentPostion -= textPathMeasure.getLength();
            if (!textPathMeasure.nextContour()) {
                break;
            }
        }
        textPathMeasure.getSegment(0, currentPostion, animalTextPath, true);
        canvas.drawPath(animalTextPath,mTextPaint);
        canvas.drawLine(0, mHeight / 2, mWidth, mHeight / 2, mPaint);
    }

    private void beginTextAnimal(float totalPathLength) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, totalPathLength);
        valueAnimator.setDuration(animalDuration);
        valueAnimator.addUpdateListener(animation -> {
            currentPostion = (float) animation.getAnimatedValue();
            invalidate();
        });
        valueAnimator.start();
    }
}
