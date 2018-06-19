package com.bian.viewapplication.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Scroller;

/**
 * Created by Bianmingliang on 2018/6/14.
 */

public class GestureViewGroup extends ViewGroup {
    private int mHeight, mWidth;
    private GestureDetector mGestureDetector;
    private DisplayMetrics mDisplayMetrics;
    private Scroller mScroller;

    public GestureViewGroup(Context context) {
        super(context);
        initView(context);
    }

    public GestureViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        mGestureDetector = new GestureDetector(getContext(), new MyGestureListener());
        mDisplayMetrics = context.getResources().getDisplayMetrics();
        mScroller = new Scroller(context, new DecelerateInterpolator());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mHeight = 0;
        mWidth = 0;
        int count = getChildCount();
        for (int index = 0; index < count; index++) {
            View childView = getChildAt(index);
            if (childView.getVisibility() != GONE) {
                measureChildWithMargins(childView, widthMeasureSpec, 0, heightMeasureSpec, 0);
                LayoutParams lp = (LayoutParams) childView.getLayoutParams();
                mWidth = Math.max(mWidth, childView.getMeasuredWidth() + lp.leftMargin + lp.topMargin);
                mHeight += childView.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
            }
        }
        mHeight = Math.max(getSuggestedMinimumHeight(), mHeight);
        mWidth = Math.max(getSuggestedMinimumWidth(), mWidth);
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int childCount = getChildCount();
        int leftPos = getPaddingLeft();
        int topPos = getPaddingTop();
        for (int index = 0; index < childCount; index++) {
            View childView = getChildAt(index);
            LayoutParams layoutParams = (LayoutParams) childView.getLayoutParams();
            topPos += layoutParams.topMargin;
            childView.layout(leftPos, topPos, leftPos + childView.getMeasuredWidth(), topPos + childView.getMeasuredHeight());
            topPos = topPos + childView.getMeasuredHeight() + layoutParams.bottomMargin;
        }
    }

    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if (getScrollY() <= 0 && distanceY < 0) {
                scrollTo(0, 0);
            } else if (getScrollY() >= getMeasuredHeight() - mDisplayMetrics.heightPixels && distanceY > 0) {
                scrollTo(0, getMeasuredHeight() - mDisplayMetrics.heightPixels);
            } else {
                scrollBy(0, (int) distanceY);
            }
            return true;
        }


        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (getScrollY() - velocityY / 2 <= 0 && velocityY > 0) {
                mScroller.startScroll(0, getScrollY(), 0, -getScrollY(), 500);
            } else if (getScrollY() - velocityY / 2 >= getMeasuredHeight() - mDisplayMetrics.heightPixels && velocityY < 0) {
                mScroller.startScroll(0, getScrollY(), 0, getMeasuredHeight() - mDisplayMetrics.heightPixels - getScrollY(), 500);
            } else {
                mScroller.startScroll(0, getScrollY(), 0, (int) (-velocityY / 2), 500);
            }
            invalidate();
            return true;
        }
    }


    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }

    @Override
    public GestureViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new GestureViewGroup.LayoutParams(getContext(), attrs);
    }

    @Override
    protected GestureViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new GestureViewGroup.LayoutParams(GestureViewGroup.LayoutParams.MATCH_PARENT, GestureViewGroup.LayoutParams.MATCH_PARENT);
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new GestureViewGroup.LayoutParams(p);
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof GestureViewGroup.LayoutParams;
    }

    public static class LayoutParams extends MarginLayoutParams {
        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }
    }
}
