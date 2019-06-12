package com.bian.viewapplication.view.layout_manager;


import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

public class MyLayoutManger extends RecyclerView.LayoutManager {
    private OrientationHelper orientationHelper;
    private LayoutState layoutState;
    /**
     * 布局正在向上滚动
     */
    public static int LAYOUT_DERATION_HEAD = -1;
    /**
     * 布局正在向下滚动
     */
    public static int LAYOUT_DERATION_TAIL = 1;

    /**
     * 布局的滚动方向
     */
    private int mLayoutScrollDiretaion;

    public MyLayoutManger() {
        orientationHelper = OrientationHelper.createVerticalHelper(this);
        layoutState = new LayoutState();
        mLayoutScrollDiretaion = LAYOUT_DERATION_TAIL;
    }

    private void ensureLayoutState() {
        layoutState.aviableSpace = orientationHelper.getEndAfterPadding() - layoutState.offSet;
        layoutState.mCurrentPosition = 0;
    }

    private View getCHildClosestToStart() {
        return getChildAt(0);
    }

    private View getChildClosestToEnd() {
        return getChildAt(getChildCount() - 1);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (state.getItemCount() == 0) {
            removeAndRecycleAllViews(recycler);
            return;
        }
        ensureLayoutState();
        while (layoutState.hasMore(state) && layoutState.aviableSpace > 0) {
            View itemView = recycler.getViewForPosition(layoutState.mCurrentPosition);
            measureChildWithMargins(itemView, 0, 0);
            addView(itemView, 0);
            int left = getPaddingLeft();
            int right = left + orientationHelper.getDecoratedMeasurementInOther(itemView);
            int top;
            int bottom;
            layoutState.itemConsumed = orientationHelper.getDecoratedMeasurement(itemView);
            if (mLayoutScrollDiretaion == LAYOUT_DERATION_TAIL) {
                top = layoutState.offSet;
                bottom = top + layoutState.itemConsumed;
                layoutState.aviableSpace = orientationHelper.getTotalSpace() - layoutState.offSet;
            } else {
                bottom = layoutState.offSet;
                top = layoutState.offSet - layoutState.itemConsumed;
            }
            layoutState.offSet += layoutState.itemConsumed * mLayoutScrollDiretaion;
            layoutDecoratedWithMargins(itemView, left, top, right, bottom);
            if (layoutState.aviableSpace > 0) {
                layoutState.mCurrentPosition += mLayoutScrollDiretaion;
            }
        }
    }

    @Override
    public void onDetachedFromWindow(RecyclerView view, RecyclerView.Recycler recycler) {
        removeAndRecycleAllViews(recycler);
    }

    @Override
    public boolean canScrollVertically() {
        return true;
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        mLayoutScrollDiretaion = dy > 0 ? LAYOUT_DERATION_TAIL : LAYOUT_DERATION_HEAD;
        return dy;
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    private class LayoutState {
        /**
         * 布局的起始位置
         */
        public int mCurrentPosition;
        /**
         * 布局的偏移量
         */
        public int offSet;
        /**
         * recyclerView可使用的距离
         */
        public int aviableSpace;
        /**
         * 被每个ItemView所消耗的距离
         */
        public int itemConsumed;
        /**
         * 手指滚过的距离
         */
        public int scrollDistance;

        public boolean hasMore(RecyclerView.State state) {
            return mCurrentPosition >= 0 && mCurrentPosition <= state.getItemCount();
        }
    }
}
