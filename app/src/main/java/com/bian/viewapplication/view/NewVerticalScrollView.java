package com.bian.viewapplication.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.EdgeEffect;
import android.widget.OverScroller;
import android.widget.ScrollView;

import com.bian.viewapplication.util.CommonLog;

public class NewVerticalScrollView extends ViewGroup {
    private int verticalScrollRange;
    private ViewConfiguration mViewConfiguration;
    private float previousY, previousX;
    /**
     * The unit we are using to track velocity
     */
    private static final int PIXELS_PER_SECOND = 1000;
    private VelocityTracker mVelocityTracker;
    private int mOverscrollDistance;
    private int mOverflingDistance;
    private OverScroller mScroller;
    private EdgeEffect mEdgeGlowTop;
    private EdgeEffect mEdgeGlowBottom;
    private int mMinimumVelocity;
    private int mMaximumVelocity;

    private ScrollView mScrollView;

    public NewVerticalScrollView(Context context) {
        this(context, null);
    }

    public NewVerticalScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NewVerticalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        setWillNotDraw(false);
        mViewConfiguration = ViewConfiguration.get(getContext());
        mVelocityTracker = VelocityTracker.obtain();
        mOverflingDistance = mViewConfiguration.getScaledOverflingDistance();
        mOverscrollDistance = mViewConfiguration.getScaledOverscrollDistance();
        mMinimumVelocity = mViewConfiguration.getScaledMinimumFlingVelocity();
        mMaximumVelocity = mViewConfiguration.getScaledMaximumFlingVelocity();
        mScroller = new OverScroller(getContext());
        setVerticalScrollBarEnabled(true);
        setWillNotDraw(false);
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
    public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        if (disallowIntercept) {
            releaseVelocityTracker();
        }
        super.requestDisallowInterceptTouchEvent(disallowIntercept);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        CommonLog.i("onLayout");
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
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean shallIntercept = false;
        if (getScrollY() == 0 && !canScrollVertically(1)) {
            return false;
        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                previousX = ev.getX();
                previousY = ev.getY();
                initVelocityTracker();
                break;
            case MotionEvent.ACTION_MOVE:
                float distanceX = ev.getX() - previousX;
                float distanceY = ev.getY() - previousY;
                shallIntercept = shallScrollVertical(distanceX, distanceY);
                previousX = ev.getX();
                previousY = ev.getY();
                break;
        }
        return shallIntercept ? shallIntercept : super.onInterceptTouchEvent(ev);
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
                    mVelocityTracker.computeCurrentVelocity(PIXELS_PER_SECOND, mMaximumVelocity);
                    onScroll(distanceY);
                    previousX = event.getX();
                    previousY = event.getY();
                    if (getScrollRange() < 0) {

                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                float xVelocity = mVelocityTracker.getXVelocity();
                float yVelocity = mVelocityTracker.getYVelocity();
                releaseVelocityTracker();
                if (shallFling(xVelocity, yVelocity) && verticalScrollRange > getMeasuredHeight()) {
                    onFling(-yVelocity);
                } else {
                    if (mScroller.springBack(getScrollX(), getScrollY(), 0, 0, 0, getScrollRange())) {
                        postInvalidateOnAnimation();
                    }
                }
                break;
        }
        return true;
    }


    private int getScrollRange() {
        return Math.max(0, verticalScrollRange - getHeight());
    }

    private void onFling(float velocityY) {
        mScroller.fling(getScrollX(), getScrollY(), 0, (int) velocityY, 0, 0, 0, verticalScrollRange, 0, getHeight() / 2);
        postInvalidateOnAnimation();
    }

    private void onScroll(float distanceY) {
        overScrollBy(0, (int) distanceY, getScrollX(), getScrollY(), 0, getScrollRange(), 0, mOverscrollDistance, true);
    }

    @Override
    public void setOverScrollMode(int mode) {
        if (mode != OVER_SCROLL_NEVER) {
            if (mEdgeGlowTop == null) {
                Context context = getContext();
                mEdgeGlowTop = new EdgeEffect(context);
                mEdgeGlowBottom = new EdgeEffect(context);
            }
        } else {
            mEdgeGlowTop = null;
            mEdgeGlowBottom = null;
        }
        super.setOverScrollMode(mode);
    }

    @Override
    protected int computeVerticalScrollRange() {
        final int count = getChildCount();
        final int contentHeight = getHeight() - getPaddingBottom() - getPaddingTop();
        if (count == 0) {
            return contentHeight;
        }
        int scrollRange = verticalScrollRange;
        final int scrollY = getScrollY();
        final int overscrollBottom = getScrollRange();
        if (scrollY < 0) {
            scrollRange -= scrollY;
        } else if (scrollY > overscrollBottom) {
            scrollRange += scrollY - overscrollBottom;
        }
        return scrollRange;
    }


    @Override
    protected void onOverScrolled(int scrollX, int scrollY,
                                  boolean clampedX, boolean clampedY) {
        // Treat animating scrolls differently; see #computeScroll() for why.
        if (!mScroller.isFinished()) {
            final int oldX = getScrollX();
            final int oldY = getScrollY();
            setScrollX(scrollX);
            setScrollY(scrollY);
            onScrollChanged(getScrollX(), getScrollY(), oldX, oldY);
            if (clampedY) {
                if (mScroller.springBack(getScrollX(), getScrollY(), 0, 0, 0, getScrollRange()))
                    postInvalidateOnAnimation();
            }
        } else {
            super.scrollTo(scrollX, scrollY);
        }
        awakenScrollBars();
    }


    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            int oldX = getScrollX();
            int oldY = getScrollY();
            int x = mScroller.getCurrX();
            int y = mScroller.getCurrY();

            if (oldX != x || oldY != y) {
                final int range = getScrollRange();
                final int overscrollMode = getOverScrollMode();
                final boolean canOverscroll = overscrollMode == OVER_SCROLL_ALWAYS ||
                        (overscrollMode == OVER_SCROLL_IF_CONTENT_SCROLLS && range > 0);

                overScrollBy(x - oldX, y - oldY, oldX, oldY, 0, range,
                        0, mOverflingDistance, false);
                onScrollChanged(getScrollX(), getScrollY(), oldX, oldY);
                if (canOverscroll) {
                    if (y < 0 && oldY >= 0) {
                        mEdgeGlowTop.onAbsorb((int) mScroller.getCurrVelocity());
                    } else if (y > range && oldY <= range) {
                        mEdgeGlowBottom.onAbsorb((int) mScroller.getCurrVelocity());
                    }
                }
            }
            if (!awakenScrollBars()) {
                // Keep on drawing until the animation has finished.
                postInvalidateOnAnimation();
            }
        }
    }

    private boolean shallFling(float xVelocity, float yVelocity) {
        return Math.abs(xVelocity) < Math.abs(yVelocity) && Math.abs(yVelocity) > mMinimumVelocity;
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
        return Math.abs(distanceY) > Math.abs(distanceX) && Math.abs(distanceY) > mViewConfiguration.getScaledTouchSlop() && verticalScrollRange > getMeasuredHeight();
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
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
