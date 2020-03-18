package com.bian.viewapplication.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by bianmingliang on 2018/6/20.
 */

public class HorizonalGroup extends ViewGroup {
    public HorizonalGroup(Context context) {
        super(context);
    }

    public HorizonalGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int maxHeight = 0, maxWidth = 0, childState = 0;
        int childCount = getChildCount();
        for (int index = 0; index < childCount; index++) {
            View childView = getChildAt(index);
            if (childView.getVisibility() != GONE) {
                measureChildWithMargins(childView, widthMeasureSpec, 0, heightMeasureSpec, 0);
                LayoutParams layoutParams = (LayoutParams) childView.getLayoutParams();
                maxWidth = maxWidth += layoutParams.leftMargin + childView.getMeasuredWidth() + layoutParams.rightMargin;
                maxHeight = Math.max(maxHeight, layoutParams.topMargin + childView.getMeasuredHeight() + layoutParams.bottomMargin);
                childState = combineMeasuredStates(childState, childView.getMeasuredState());
            }
        }
        maxWidth = Math.max(maxWidth, getSuggestedMinimumWidth());
        maxHeight = Math.max(maxHeight, getSuggestedMinimumHeight());
        setMeasuredDimension(resolveSizeAndState(maxWidth, widthMeasureSpec, childState),
                resolveSizeAndState(maxHeight, heightMeasureSpec,
                        childState << MEASURED_HEIGHT_STATE_SHIFT));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int initTop = getPaddingTop();
        int initLeft = getPaddingLeft();
        int childCount = getChildCount();
        for (int index = 0; index < childCount; index++) {
            View childView = getChildAt(index);
            LayoutParams layoutParams = (LayoutParams) childView.getLayoutParams();
            initLeft += layoutParams.leftMargin;
            childView.layout(initLeft, initTop + layoutParams.topMargin, initLeft + childView.getMeasuredWidth(), initTop + layoutParams.topMargin + childView.getMeasuredHeight());
            initLeft += childView.getMeasuredWidth() + layoutParams.rightMargin;
        }
    }

    @Override
    public HorizonalGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new HorizonalGroup.LayoutParams(getContext(), attrs);
    }

    @Override
    protected HorizonalGroup.LayoutParams generateDefaultLayoutParams() {
        return new HorizonalGroup.LayoutParams(HorizonalGroup.LayoutParams.MATCH_PARENT, HorizonalGroup.LayoutParams.MATCH_PARENT);
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new HorizonalGroup.LayoutParams(p);
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof HorizonalGroup.LayoutParams;
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
