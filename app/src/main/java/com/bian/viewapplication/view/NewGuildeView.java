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
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.bian.viewapplication.R;
import com.bian.viewapplication.bean.ViewLoactionBean;
import com.bian.viewapplication.util.CommonLog;

/**
 * Created by Administrator on 2018/6/26.
 */

public class NewGuildeView extends View {
    private int mHeight, mWidth;
    private RectF viewRectF;
    private Paint mPaint;
    private int guildBackgoundColor;
    private ViewLoactionBean targetViewLocatio;
    private PorterDuffXfermode clearMode;
    private RectF originRectF;
    private RectF targetRectF;
    private RectF bottomRectF;
    public static final int CENTER = 1;
    public static final int START = 2;
    public static final int END = 3;
    private int guildeTipAlign;
    private float guildeLineLength;
    private Path guildePath;
    private boolean mDrawPath;

    public NewGuildeView(Context context) {
        this(context, null);
    }

    public NewGuildeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
        if (attrs != null) {
            initAttrs(attrs);
        }
    }

    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setStrokeWidth(getResources().getDimension(R.dimen.dp1));
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.NewGuildeView);
        guildBackgoundColor = typedArray.getColor(R.styleable.NewGuildeView_guildBackgoundColor, Color.parseColor("#66000000"));
        guildeTipAlign = typedArray.getInt(R.styleable.NewGuildeView_tip_align, CENTER);
        guildeLineLength = typedArray.getDimension(R.styleable.NewGuildeView_guilde_lenth, 100f);
        typedArray.recycle();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        viewRectF = new RectF(0, 0, mWidth, mHeight);
        clearMode = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);
        bottomRectF = new RectF();
        guildePath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setColor(Color.RED);
        canvas.drawLine(0, 0, mWidth, 0, mPaint);
        int count;
        if (targetViewLocatio != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                count = canvas.saveLayer(viewRectF, mPaint);
            } else {
                count = canvas.saveLayer(viewRectF, mPaint, Canvas.ALL_SAVE_FLAG);
            }
            canvas.drawColor(guildBackgoundColor);
            mPaint.setXfermode(clearMode);
            canvas.drawRoundRect(targetRectF, 20, 20, mPaint);
            canvas.restoreToCount(count);
            mPaint.setXfermode(null);
        }
        if (mDrawPath) {
            canvas.drawPath(guildePath,mPaint);
        }
        canvas.drawRect(bottomRectF, mPaint);
    }

    public void setTargetView(ViewLoactionBean location) {
        targetViewLocatio = location;
        CommonLog.i("statusBarHeight:" + location.getStatusBarHeight());
        bottomRectF = new RectF(0, mHeight - location.getStatusBarHeight() - 10, mWidth, mHeight);
        originRectF = new RectF(location.getViewLeftLocation(), location.getViewTopLocation() - location.getStatusBarHeight(), location.getViewLeftLocation() + location.getViewWidth(), location.getViewTopLocation() + location.getViewHeight() - location.getStatusBarHeight());
        targetRectF = new RectF();
        beginShowCaseAnimal();
    }

    private void beginShowCaseAnimal() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0f, 1f);
        valueAnimator.setDuration(2000);
        valueAnimator.addUpdateListener(animation -> {
            float factor = (float) animation.getAnimatedValue();
            zoomRectF(factor);
            invalidate();
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                beginLineAnimal();
            }
        });
        valueAnimator.start();
    }

    private void beginLineAnimal() {
        mDrawPath = true;
        float beginX;
        switch (guildeTipAlign) {
            case 1:
                beginX = (targetRectF.right + targetRectF.left) / 2;
                break;
            case 2:
                beginX = targetRectF.left;
                break;
            default:
                beginX = targetRectF.right;
                break;
        }
        guildePath.moveTo(beginX, targetRectF.bottom);
        guildePath.lineTo(beginX, targetRectF.bottom + guildeLineLength);
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
