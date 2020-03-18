package com.bian.viewapplication.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.bian.viewapplication.R;

/**
 * Created by bianmingliang on 2018/6/7.
 */

public class StepOneView extends View {
    private Paint mPaint;
    private int mHeight;
    private int mWidth;
    private RectF boundRect;
    private RectF targetRectF;
    private Paint linePaint;
    private int diffHeight;
    private String mTips;
    private int guildeLineLength;
    private float guildeLineX;
    private TextPaint textPaint;
    private float textLength;
    private Path guildePath;
    private Path realGuildePath;
    private float guildeCircle;
    private float textMargin;
    private RectF originRectF;
    public long duration;
    private PathMeasure linePathMeasure;
    private float lineDrawLength;
    private float textDrawLength;
    private float mLengthSum;
    private Path mTextPath;
    private Path mRealTextPath;
    private PathMeasure textPathMeasure;
    private float textPathLength;

    public StepOneView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public StepOneView(Context context) {
        super(context);
        initPaint();
    }

    private void initPaint() {
        duration = 1000;
        textMargin = getContext().getResources().getDimensionPixelSize(R.dimen.dp20);
        guildeLineLength = getContext().getResources().getDimensionPixelSize(R.dimen.dp40);
        guildeCircle = getContext().getResources().getDimension(R.dimen.dp10);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        linePaint.setStrokeWidth(getResources().getDimension(R.dimen.dp2));
        linePaint.setColor(Color.WHITE);
        textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(getContext().getResources().getDimension(R.dimen.sp20));
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setColor(ContextCompat.getColor(getContext(), R.color.white));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHeight = getMeasuredHeight();
        mWidth = getMeasuredWidth();
        boundRect = new RectF(0, 0, mWidth, mHeight);
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        diffHeight = displayMetrics.heightPixels - mHeight;
        guildePath = new Path();
        realGuildePath = new Path();
        linePathMeasure = new PathMeasure();
        mRealTextPath = new Path();
        textPathMeasure = new PathMeasure();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setColor(ContextCompat.getColor(getContext(), R.color.black));
        mPaint.setAlpha(200);
        if (targetRectF != null) {
            int count;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                count = canvas.saveLayer(boundRect, mPaint);
            } else {
                count = canvas.saveLayer(boundRect, mPaint, Canvas.ALL_SAVE_FLAG);
            }
            canvas.drawRect(0, 0, mWidth, mHeight, mPaint);
            mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
            mPaint.setColor(Color.TRANSPARENT);
            mPaint.setAlpha(255);
            canvas.drawOval(targetRectF, mPaint);
            canvas.restoreToCount(count);
            mPaint.setXfermode(null);
            mPaint.setColor(Color.WHITE);
            drawGuildeLine(canvas);
            if (mTextPath != null) {
                drawTipsText(canvas);
            }
        }
    }

    private void drawTipsText(Canvas canvas) {
        mRealTextPath.reset();
        textPathMeasure.setPath(mTextPath, false);
        while (textDrawLength > textPathMeasure.getLength()) {
            textPathMeasure.getSegment(0, textPathMeasure.getLength(), mRealTextPath, true);
            textDrawLength = textDrawLength - textPathMeasure.getLength();
            if (!textPathMeasure.nextContour()) {
                break;
            }
        }
        textPathMeasure.getSegment(0, textDrawLength, mRealTextPath, true);
        canvas.drawPath(mRealTextPath, textPaint);
    }

    private void drawGuildeLine(Canvas canvas) {
        linePathMeasure.setPath(guildePath, false);
        while (lineDrawLength > linePathMeasure.getLength()) {
            linePathMeasure.getSegment(0, linePathMeasure.getLength(), realGuildePath, true);
            lineDrawLength = lineDrawLength - linePathMeasure.getLength();
            if (!linePathMeasure.nextContour()) {
                break;
            }
        }
        linePathMeasure.getSegment(0, lineDrawLength, realGuildePath, true);
        canvas.drawPath(realGuildePath, linePaint);
    }

    public void performGuide(final View view, String tip) {
        mTips = tip;
        textLength = textPaint.measureText(mTips);
        view.post(() -> {
            int targetWidth = view.getMeasuredWidth();
            int targetHeight = view.getMeasuredHeight();
            int location[] = new int[2];
            view.getLocationOnScreen(location);
            targetRectF = new RectF();
            originRectF = new RectF(location[0], location[1] - diffHeight, location[0] + targetWidth, location[1] + targetHeight - diffHeight);
            beginShowCaseAnimator();
        });
    }


    private void beginShowCaseAnimator() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0f, 1.35f);
        valueAnimator.setDuration(duration);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float factor = (float) animation.getAnimatedValue();
                zoomRectF(factor);
                guildeLineX = targetRectF.left + (targetRectF.right - targetRectF.left) / 2;
                invalidate();
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                onCaseAnimationEnd();
            }
        });
        valueAnimator.start();
    }

    private void onCaseAnimationEnd() {
        guildePath.reset();
        guildePath.moveTo(guildeLineX, targetRectF.top);
        guildePath.lineTo(guildeLineX, targetRectF.top - guildeLineLength);
        guildePath.addCircle(guildeLineX, targetRectF.top - guildeLineLength - guildeCircle, guildeCircle, Path.Direction.CW);
        linePathMeasure.setPath(guildePath, false);
        mLengthSum = linePathMeasure.getLength();
        while (linePathMeasure.nextContour()) {
            mLengthSum += linePathMeasure.getLength();
        }
        beginGuildeLineAnimator();
    }

    private void beginGuildeLineAnimator() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, mLengthSum);
        valueAnimator.setDuration(duration);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                lineDrawLength = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                onGuildeLineAnimalEnd();
            }
        });
        valueAnimator.start();
    }

    private void onGuildeLineAnimalEnd() {
        mTextPath = new Path();
        textPaint.getTextPath(mTips, 0, mTips.length(), guildeLineX, targetRectF.top - guildeLineLength - guildeCircle * 2 - textMargin, mTextPath);
        textPathMeasure.setPath(mTextPath, false);
        textPathLength = textPathMeasure.getLength();
        while (textPathMeasure.nextContour()) {
            textPathLength += textPathMeasure.getLength();
        }
        beginTextAnimal();
    }

    private void beginTextAnimal() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, textPathLength);
        valueAnimator.setDuration(duration);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                textDrawLength = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.start();
    }


    /**
     * 按照比例放大RectF区域
     *
     * @param factor
     */
    private void zoomRectF(double factor) {
        float height = Math.abs(originRectF.top - originRectF.bottom);
        float width = Math.abs(originRectF.left - originRectF.right);
        double subHeight = (height * factor - height) / 2;
        double subWidth = (width * factor - width) / 2;
        targetRectF.top = (float) (originRectF.top - subHeight);
        targetRectF.bottom = (float) (originRectF.bottom + subHeight);
        targetRectF.left = (float) (originRectF.left - subWidth);
        targetRectF.right = (float) (originRectF.right + subWidth);
    }
}
