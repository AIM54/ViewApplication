package com.bian.viewapplication.behavior;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.bian.viewapplication.util.CommonLog;

/**
 * Created by Administrator on 2018/11/2.
 */

public class HeaderBehavior extends CoordinatorLayout.Behavior<ImageView> {
    public HeaderBehavior() {
    }

    public HeaderBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull ImageView child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        return (axes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull ImageView child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);
        if (target instanceof RecyclerView) {
            RecyclerView recyclerView = (RecyclerView) target;
            if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                onLinearNestedPreScroll(child, layoutManager, dx, dy, consumed, recyclerView.getAdapter().getItemCount());
            } else if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
                GridLayoutManager gridLayoutManager = (GridLayoutManager) recyclerView.getLayoutManager();

            } else if (recyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
                StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();

            }
        }
    }

    /**
     * '
     * 处理LinearLayoutManager嵌套滚动的情况
     *
     * @param child
     * @param target
     * @param dx
     * @param dy
     * @param consumed
     * @param itemCount
     */
    private void onLinearNestedPreScroll(ImageView child, LinearLayoutManager target, int dx, int dy, int[] consumed, int itemCount) {
        if (target.findFirstCompletelyVisibleItemPosition() == 0) {
            if (child.getBottom() + child.getTranslationY() >= 0 && child.getTop() + child.getTranslationY() <= 0) {
                if (child.getTranslationY() + child.getBottom() - dy < 0) {
                    child.setTranslationY(-child.getBottom());
                } else if (child.getTranslationY() + child.getTop() - dy > 0) {
                    child.setTranslationY(0);
                } else {
                    child.setTranslationY(child.getTranslationY() - dy);
                    consumed[1] = dy;
                }
            }
        }

    }

    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull ImageView child, @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type);
        CommonLog.i("onNestedScroll");
    }
}
