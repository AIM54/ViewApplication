package com.bian.viewapplication.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.bian.viewapplication.R;

/**
 * Created by bianmingliang on 2018/6/15.
 */

public class DragViewGroup extends ViewGroup {
    private ViewDragHelper mViewDragHelper;
    private DisplayMetrics mDisplayMetrics;
    private int mRvScrolledLength;
    private int mTopPostion;
    private Paint mPaint;
    private Paint linePaint;
    /**
     * 大圆直径和屏幕宽度的比例
     */
    private float bigCircleRatio = 1f / 4f;
    private float smallCircleRatio = 1f / 2f;

    public DragViewGroup(Context context) {
        super(context);
        initGroup(context);
    }

    public DragViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        initGroup(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int childCount = getChildCount();
        int width = 0, height = 0, childState = 0;
        for (int index = 0; index < childCount; index++) {
            View childView = getChildAt(index);
            if (childView.getVisibility() != GONE) {
                measureChildWithMargins(childView, widthMeasureSpec, 0, heightMeasureSpec, 0);
                LayoutParams layoutParams = (LayoutParams) childView.getLayoutParams();
                width = Math.max(width, layoutParams.leftMargin + layoutParams.rightMargin + childView.getMeasuredWidth());
                height = Math.max(height, layoutParams.topMargin + layoutParams.bottomMargin + childView.getMeasuredHeight());
                childState = combineMeasuredStates(childState, childView.getMeasuredState());
            }
        }
        setMeasuredDimension(resolveSizeAndState(width, widthMeasureSpec, childState),
                resolveSizeAndState(height, heightMeasureSpec,
                        childState << MEASURED_HEIGHT_STATE_SHIFT));
    }

    private void initGroup(Context context) {
        mViewDragHelper = ViewDragHelper.create(this, new MyCallBack());
        mDisplayMetrics = context.getResources().getDisplayMetrics();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.GREEN);
        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(getContext().getResources().getDimension(R.dimen.dp4));
        linePaint.setColor(Color.RED);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int topPos = getPaddingTop();
        int leftPos = getPaddingLeft();
        for (int index = 0; index < getChildCount(); index++) {
            View childView = getChildAt(index);
            LayoutParams layoutParams = (LayoutParams) childView.getLayoutParams();
            childView.layout(leftPos + layoutParams.leftMargin, topPos + layoutParams.topMargin, leftPos + layoutParams.leftMargin + childView.getMeasuredWidth(), topPos + layoutParams.topMargin + childView.getMeasuredHeight());
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (mTopPostion >= 0) {
            if (mTopPostion < getMeasuredWidth() * bigCircleRatio) {
                canvas.drawCircle(getMeasuredWidth() / 2, mTopPostion / 2, mTopPostion / 2, mPaint);
            } else {
                canvas.drawCircle(getMeasuredWidth() / 2, mTopPostion - getMeasuredWidth() * bigCircleRatio / 2, getMeasuredWidth() * bigCircleRatio / 2, mPaint);
                float smallRadius=(mTopPostion-getMeasuredWidth()*bigCircleRatio)/2;
                canvas.drawCircle(getMeasuredWidth()/2,smallRadius,smallRadius,mPaint);
            }
        } else {
            int radius = Math.min(Math.abs(mTopPostion), getMeasuredWidth()) / 2;
            canvas.drawCircle(getMeasuredWidth() / 2, getMeasuredHeight() + mTopPostion / 2, radius, mPaint);
        }
        canvas.drawLine(0, getMeasuredHeight(), getMeasuredWidth(), getMeasuredHeight(), linePaint);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mViewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        if (mViewDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    private class MyCallBack extends ViewDragHelper.Callback {

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return child instanceof RecyclerView;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            if (child instanceof RecyclerView) {
                RecyclerView recyclerView = (RecyclerView) child;
                recyclerView.addOnScrollListener(new MyOnScrollerListener());
                if (top <= 0 && recyclerView.computeVerticalScrollRange() - mRvScrolledLength > recyclerView.getMeasuredHeight()) {
                    top = 0;
                } else if (top > 0 && mRvScrolledLength > 0) {
                    top = 0;
                }
            }
            return top;
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            mTopPostion = top;
            invalidate();
        }

        @Override
        public int getViewVerticalDragRange(View child) {
            return child.getMeasuredHeight();
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            mViewDragHelper.smoothSlideViewTo(releasedChild, 0, 0);
            invalidate();
        }
    }

    private class MyOnScrollerListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            mRvScrolledLength += dy;
        }
    }

    @Override
    public DragViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new DragViewGroup.LayoutParams(getContext(), attrs);
    }

    @Override
    protected DragViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new DragViewGroup.LayoutParams(DragViewGroup.LayoutParams.MATCH_PARENT, DragViewGroup.LayoutParams.MATCH_PARENT);
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new DragViewGroup.LayoutParams(p);
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof DragViewGroup.LayoutParams;
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
