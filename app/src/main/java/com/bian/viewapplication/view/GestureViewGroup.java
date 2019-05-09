package com.bian.viewapplication.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.Scroller;

import com.bian.viewapplication.util.CommonLog;

/**
 * Created by Bianmingliang on 2018/6/14.
 */

public class GestureViewGroup extends ViewGroup {
    private GestureDetector mGestureDetector;
    private DisplayMetrics mDisplayMetrics;
    private Scroller mScroller;
    private int verticalScrollRange;
    private boolean shouldIntercept;

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
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        mGestureDetector.onTouchEvent(ev);
        return shouldIntercept;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = 0;
        int width = 0;
        int childState = 0;
        int count = getChildCount();
        for (int index = 0; index < count; index++) {
            View childView = getChildAt(index);
            if (childView.getVisibility() != GONE) {
                measureChildWithMargins(childView, widthMeasureSpec, 0, heightMeasureSpec, 0);
                LayoutParams lp = (LayoutParams) childView.getLayoutParams();
                width = Math.max(width, childView.getMeasuredWidth() + lp.leftMargin + lp.topMargin);
                height += childView.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
                childState = combineMeasuredStates(childState, childView.getMeasuredState());
            }
        }
        height = Math.max(getSuggestedMinimumHeight(), height + getPaddingTop() + getPaddingBottom());
        width = Math.max(getSuggestedMinimumWidth(), width + getPaddingLeft() + getPaddingRight());
        verticalScrollRange = height;
        setMeasuredDimension(resolveSizeAndState(width, widthMeasureSpec, childState),
                resolveSizeAndState(height, heightMeasureSpec,
                        childState << MEASURED_HEIGHT_STATE_SHIFT));
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
        int rightPos = right - getPaddingRight();
        for (int index = 0; index < childCount; index++) {
            View childView = getChildAt(index);
            LayoutParams layoutParams = (LayoutParams) childView.getLayoutParams();
            topPos += layoutParams.topMargin;
            int childRightPos = Math.min(leftPos + layoutParams.leftMargin + childView.getMeasuredWidth(), rightPos);
            childView.layout(leftPos + layoutParams.leftMargin, topPos, childRightPos, topPos + childView.getMeasuredHeight());
            topPos = topPos + childView.getMeasuredHeight() + layoutParams.bottomMargin;
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDown(MotionEvent e) {
            shouldIntercept=false;
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if (verticalScrollRange>getHeight()){
                shouldIntercept=true;
                if (verticalScrollRange - getHeight() > 0) {
                    if (getScrollY() <= 0 && distanceY < 0) {
                        scrollTo(0, 0);
                    } else if (getScrollY() >= verticalScrollRange - getHeight() && distanceY > 0) {
                        scrollTo(0, verticalScrollRange - getHeight());
                    } else {
                        scrollBy(0, (int) distanceY);
                    }
                }
                return true;
            }
           return false;
        }


        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            shouldIntercept=true;
            if (verticalScrollRange - getHeight() > 0) {
                if (getScrollY() - velocityY / 2 <= 0 && velocityY > 0) {
                    mScroller.startScroll(0, getScrollY(), 0, -getScrollY(), 500);
                } else if (getScrollY() - velocityY / 2 >= verticalScrollRange - getHeight() && velocityY < 0) {
                    mScroller.startScroll(0, getScrollY(), 0, verticalScrollRange - getHeight() - getScrollY(), 500);
                } else {
                    mScroller.startScroll(0, getScrollY(), 0, (int) (-velocityY / 2), 500);
                }
                invalidate();
                return true;
            }
            return false;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            return super.onSingleTapConfirmed(e);
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
