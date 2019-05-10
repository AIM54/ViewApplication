package com.bian.viewapplication.view;


import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ScrollView;
import android.widget.Scroller;

import com.bian.viewapplication.util.CommonLog;

/**
 * Created by bianmingliang on 2018/6/30.
 */

public class VerticalScrollView extends ViewGroup {
    private int verticalScrollRange;
    private ViewConfiguration mViewConfiguration;
    private float previousY, previousX;
    private Scroller mScroller;
    private ScrollView mScrollView;
    /**
     * The unit we are using to track velocity
     */
    private static final int PIXELS_PER_SECOND = 1000;
    private VelocityTracker mVelocityTracker;

    public VerticalScrollView(Context context) {
        this(context, null);
    }

    public VerticalScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerticalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        mViewConfiguration = ViewConfiguration.get(getContext());
        mVelocityTracker = VelocityTracker.obtain();
        mScroller = new Scroller(getContext(), new DecelerateInterpolator());
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        CommonLog.i("event.getAction():" + ev.getAction());
        boolean shallIntercept = false;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                previousX = ev.getX();
                previousY = ev.getY();
                initVelocityTracker();
                break;
            case MotionEvent.ACTION_MOVE:
                float distanceX = ev.getX() - previousX;
                float distanceY = ev.getY() - previousY;
                CommonLog.i(distanceX + "||" + distanceY);
                shallIntercept = shallScrollVertical(distanceX, distanceY);
                previousX = ev.getX();
                previousY = ev.getY();
                break;
        }
        return shallIntercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                float distanceX = previousX - event.getX();
                float distanceY = previousY - event.getY();
                if (shallScrollVertical(distanceX, distanceY)) {
                    initVelocityTracker();
                    mVelocityTracker.addMovement(event);
                    mVelocityTracker.computeCurrentVelocity(PIXELS_PER_SECOND);
                    onScroll(distanceY);
                    previousX = event.getX();
                    previousY = event.getY();
                }
                break;
            case MotionEvent.ACTION_UP:
                float xVelocity = mVelocityTracker.getXVelocity();
                float yVelocity = mVelocityTracker.getYVelocity();
                releaseVelocityTracker();
                CommonLog.i("xVelocity:" + xVelocity + "||yVelocity:" + yVelocity);
                if (shallFling(xVelocity, yVelocity)&&verticalScrollRange>getMeasuredHeight()) {
                    onFling(yVelocity);
                }
                break;
        }
        return true;
    }

    private void onScroll(float distanceY) {
        if (verticalScrollRange>getMeasuredHeight()){
            if (getScrollY() + distanceY <= 0) {
                scrollTo(0, 0);
            } else if (getScrollY() + distanceY >= verticalScrollRange-getMeasuredHeight()) {
                scrollTo(0, verticalScrollRange-getMeasuredHeight());
            } else {
                scrollBy(0, (int) distanceY);
            }
        }

    }

    private boolean shallFling(float xVelocity, float yVelocity) {
        return Math.abs(xVelocity) < Math.abs(yVelocity) && Math.abs(yVelocity) > mViewConfiguration.getScaledMinimumFlingVelocity();
    }

    private void onFling(float velocityY) {
        if (verticalScrollRange - getHeight() > 0) {
            if (getScrollY() - velocityY / 2 <= 0 && velocityY > 0) {
                mScroller.startScroll(0, getScrollY(), 0, -getScrollY(), 1000);
            } else if (getScrollY() - velocityY / 2 >= verticalScrollRange - getHeight() && velocityY < 0) {
                mScroller.startScroll(0, getScrollY(), 0, verticalScrollRange - getHeight() - getScrollY(), 1000);
            } else {
                mScroller.startScroll(0, getScrollY(), 0, (int) (-velocityY / 2), 1000);
            }
        }

    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }

    private void initVelocityTracker() {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
    }

    private void releaseVelocityTracker() {
        mVelocityTracker.recycle();
        mVelocityTracker = null;
    }

    private boolean shallScrollVertical(float distanceX, float distanceY) {
        return Math.abs(distanceY) > Math.abs(distanceX) && Math.abs(distanceY) > mViewConfiguration.getScaledTouchSlop()&&verticalScrollRange>getMeasuredHeight();
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
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new VerticalScrollView.LayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    /**
     * Custom per-child layout information.
     */
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
