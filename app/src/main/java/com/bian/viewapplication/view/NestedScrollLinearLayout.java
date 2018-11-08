package com.bian.viewapplication.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Scroller;

import com.bian.viewapplication.util.CommonLog;

/**
 * Created by Administrator on 2018/11/6.
 */

public class NestedScrollLinearLayout extends LinearLayout implements NestedScrollingParent {
    private NestedScrollingParentHelper nestedScrollingParentHelper;

    private Scroller mScroller;

    public NestedScrollLinearLayout(Context context) {
        super(context);
        nestedScrollingParentHelper = new NestedScrollingParentHelper(this);
        mScroller = new Scroller(context);
    }

    public NestedScrollLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        nestedScrollingParentHelper = new NestedScrollingParentHelper(this);
        mScroller = new Scroller(context);
    }

    public NestedScrollLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        nestedScrollingParentHelper = new NestedScrollingParentHelper(this);
        mScroller = new Scroller(context);
    }

    @Override
    public boolean onStartNestedScroll(@NonNull View child, @NonNull View target, int axes) {
        return false;
    }

    @Override
    public void onNestedScrollAccepted(@NonNull View child, @NonNull View target, int axes) {
        nestedScrollingParentHelper.onNestedScrollAccepted(child, target, axes);
    }

    @Override
    public void onStopNestedScroll(@NonNull View target) {
        nestedScrollingParentHelper.onStopNestedScroll(target);
    }

    @Override
    public void onNestedScroll(@NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        CommonLog.i("onNestedScroll");
    }

    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed) {
        CommonLog.i("onNestedPreScroll");

    }

    @Override
    public boolean onNestedFling(@NonNull View target, float velocityX, float velocityY, boolean consumed) {
        CommonLog.i("onNestedFling");
        return false;
    }

    @Override
    public boolean onNestedPreFling(@NonNull View target, float velocityX, float velocityY) {
        CommonLog.i("onNestedPreFling");
        return false;
    }

    @Override
    public int getNestedScrollAxes() {
        CommonLog.i("getNestedScrollAxes");
        return 0;
    }
}
