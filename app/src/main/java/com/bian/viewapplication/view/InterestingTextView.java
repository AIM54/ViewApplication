package com.bian.viewapplication.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
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
    private PathMeasure textPathMeasure;
    private float currentPostion;
    private long animalDuration;
    private Paint.FontMetrics fontMetrics;
    private int mHeight, mWidth;
    private boolean hasFinishTextDraw = false;
    private int waveBaseLine;
    private int waveCount = 8;
    private int waveBeginPostion = 0;
    private Path wavePath;
    private int waveHeight;

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
        mTextPaint.setStrokeWidth(getResources().getDimension(R.dimen.dp4));
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setStyle(Paint.Style.STROKE);
        fontMetrics = mTextPaint.getFontMetrics();
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
        waveBaseLine = mHeight / 2;
        wavePath = new Path();
        waveHeight = getHeight() / 2;
        waveBeginPostion = -getMeasuredWidth();
        mTextPaint.getTextPath(message, 0, message.length(), mWidth / 2, (mHeight - mTextPaint.ascent() - mTextPaint.descent()) / 2, tempTextPath);
        textPathMeasure = new PathMeasure(tempTextPath, false);
        float totalPathLength = textPathMeasure.getLength();
        while (textPathMeasure.nextContour()) {
            totalPathLength += textPathMeasure.getLength();
        }
        CommonLog.i("totalPathLength:" + totalPathLength);
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
        if (hasFinishTextDraw) {
            canvas.drawPath(tempTextPath, mTextPaint);
            wavePath.reset();
            int subLength = mWidth / waveCount;
            wavePath.moveTo(waveBeginPostion, waveBaseLine);
            for (int index = 0; index < waveCount * 2; index++) {
                int tempPoint = index * subLength + waveBeginPostion;
                wavePath.quadTo(tempPoint + subLength / 4, waveBaseLine + waveHeight, tempPoint + subLength / 2, waveBaseLine);
                wavePath.quadTo(tempPoint + subLength * 3 / 4, waveBaseLine - waveHeight, tempPoint + subLength, waveBaseLine);
            }
            canvas.drawPath(wavePath, mPaint);
        } else {
            canvas.drawPath(animalTextPath, mTextPaint);
        }
    }

    private void beginTextAnimal(float totalPathLength) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, totalPathLength);
        valueAnimator.setDuration(animalDuration);
        valueAnimator.addUpdateListener(animation -> {
            currentPostion = (float) animation.getAnimatedValue();
            invalidate();
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                hasFinishTextDraw = true;
                beginLoading();
            }
        });
        valueAnimator.start();
    }

    private void beginLoading() {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(-mWidth, 0);
        valueAnimator.addUpdateListener(animation -> {
            waveBeginPostion = (int) animation.getAnimatedValue();
            invalidate();
        });
        valueAnimator.setDuration(animalDuration);
        valueAnimator.start();
    }
}
