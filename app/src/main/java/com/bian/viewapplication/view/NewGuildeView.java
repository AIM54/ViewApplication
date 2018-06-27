package com.bian.viewapplication.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

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
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        viewRectF = new RectF(0, 0, mWidth, mHeight);
        clearMode = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);
        bottomRectF = new RectF();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        CommonLog.i("mWidth:" + mWidth + "||mHeight:" + mHeight);
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
