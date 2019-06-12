package com.bian.viewapplication.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import com.bian.viewapplication.R;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

public class MyCameraView extends View {
    private Drawable mDrawable;
    private Camera mCamera;
    private Paint mPaint;
    private int mDegree;


    public MyCameraView(Context context) {
        this(context, null);
    }

    public MyCameraView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyCameraView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.MyCameraView);
        mDrawable = typedArray.getDrawable(R.styleable.MyCameraView_img_src);
        typedArray.recycle();
        initCamera();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mDrawable != null) {
            int viewWidth = resolveSize(mDrawable.getIntrinsicWidth(), widthMeasureSpec);
            int viewHeight = resolveSize(mDrawable.getIntrinsicHeight(), heightMeasureSpec);
            setMeasuredDimension(viewWidth, viewHeight);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    private void initCamera() {
        mCamera = new Camera();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.GREEN);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mDegree=30;
//        initValueAnimator();
    }

    private void initValueAnimator() {
        ValueAnimator valueAnimator=ValueAnimator.ofInt(0,360);
        valueAnimator.setDuration(1500);
        valueAnimator.addUpdateListener(animation -> {
            mDegree= (int) animation.getAnimatedValue();
            invalidate();
        });
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.start();
    }

    public void setImageResource(@DrawableRes int imageSrc) {
        mDrawable = ContextCompat.getDrawable(getContext(), imageSrc);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mDrawable.setBounds(0, 0, getWidth(), getHeight());
        canvas.save();
        mCamera.save();
        mCamera.setLocation(0,0,-30);
        mCamera.rotateY(mDegree);
        canvas.translate(getWidth()/2,0);
        mCamera.applyToCanvas(canvas);
        canvas.translate(-getWidth()/2,0);
        mCamera.restore();
        mDrawable.draw(canvas);
        canvas.restore();
    }
}
