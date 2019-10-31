package com.bian.viewapplication.view.layout_manager;

import android.view.View;
import android.view.ViewGroup;

import com.bian.viewapplication.util.CommonLog;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
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

    private AnchorInfo mAnchor;


    public MyLayoutManger() {
        orientationHelper = OrientationHelper.createVerticalHelper(this);
        mLayoutScrollDiretaion = LAYOUT_DERATION_TAIL;
    }

    private void ensureLayoutState() {
        if (layoutState == null) {
            layoutState = new LayoutState();
            layoutState.reset();
            mAnchor = new AnchorInfo();
        } else {
            layoutState.reset();
            layoutState.mCurrentPosition = mAnchor.mAnchorPosition;
            if (mLayoutScrollDiretaion == LAYOUT_DERATION_TAIL) {
                layoutState.secondOffSet = mAnchor.mAnchorOffset;
            } else {
                layoutState.offSet = mAnchor.mAnchorOffset;
            }
        }
    }

    private View getChildClosestToStart() {
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
        detachAndScrapAttachedViews(recycler);
        fillContent(recycler, state);
    }


    /**
     * 这里为notifyDataSetChanged做一些动作
     *
     * @param recycler
     * @param state
     */
    private void layoutForPredictiveAnimations(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (!state.willRunPredictiveAnimations() || getChildCount() == 0 || state.isPreLayout()
                || !supportsPredictiveItemAnimations()) {
            return;
        }
        //因为notifyItemChanged会导致重新Layout，所以在onLayoutChildren方法中通过 detachAndScrapAttachedViews(recycler)的方式
        //将视图暂时缓存起来
        List<RecyclerView.ViewHolder> scrapViewList = recycler.getScrapList();
        layoutState.mScrapList = scrapViewList;
        if (layoutState.mScrapList != null && layoutState.mScrapList.size() > 0) {
            layoutState.reset();
            detachAndScrapAttachedViews(recycler);
        }
        fillContent(recycler, state);
        layoutState.mScrapList = null;
    }

    @Override
    public boolean supportsPredictiveItemAnimations() {
        return true;
    }

    /**
     * 用户手动划过了一定的距离，我们要在这里更新下布局的状态，进行状态的变更
     *
     * @param abs
     * @param state
     */
    private void udpateLayoutState(int abs, RecyclerView.State state) {
        if (mLayoutScrollDiretaion == LAYOUT_DERATION_TAIL) {
            layoutState.mCurrentPosition = getChildClosestToEnd() == null ? 0 : getPosition(getChildClosestToEnd()) + mLayoutScrollDiretaion;
            layoutState.offSet = orientationHelper.getDecoratedEnd(getChildClosestToEnd());
            layoutState.scrollDistance = layoutState.offSet - orientationHelper.getEndAfterPadding();
            //已经不需要添加新的Item了
            if (layoutState.mCurrentPosition >= state.getItemCount()) {
                int lastItemoffert = orientationHelper.getDecoratedStart(getChildClosestToEnd());
                layoutState.scrollDistance = Math.min(lastItemoffert, abs);
            }
        } else {
            layoutState.mCurrentPosition = getChildClosestToStart() == null ? 0 : getPosition(getChildClosestToStart()) + mLayoutScrollDiretaion;
            layoutState.offSet = orientationHelper.getStartAfterPadding();
            layoutState.scrollDistance = 0;
            if (shallScrollToHead()) {
                layoutState.scrollDistance = orientationHelper.getDecoratedEnd(getChildAt(0)) - orientationHelper.getDecoratedStart(getChildAt(1));
            }
        }
        //因为用户的手指进行了滑动所以布局的可用空间肯定是变大了，所以可用的空间起始量应该设置为手指划过的距离
        layoutState.aviableSpace = abs;
        layoutState.aviableSpace -= layoutState.scrollDistance;
    }

    /**
     * 判断这个时候是不是应该刷新头部
     *
     * @return
     */
    private boolean shallScrollToHead() {
        return getChildCount() >= 2
                && (getPosition(getChildAt(0)) != 0 ||
                orientationHelper.getDecoratedEnd(getChildAt(0)) > orientationHelper.getDecoratedStart(getChildAt(1)));
    }

    /**
     * 去填充界面
     *
     * @param recycler
     * @param state
     */
    private int fillContent(RecyclerView.Recycler recycler, RecyclerView.State state) {
        int startValue = layoutState.aviableSpace;
        if (layoutState.scrollDistance != LayoutState.NOT_SCROLL_AT_ALL) {
            if (layoutState.aviableSpace < 0) {
                layoutState.scrollDistance += layoutState.aviableSpace;
            }
            recyclerViewsFromContent(recycler);
        }
        while (layoutState.hasMore(state) && layoutState.aviableSpace > 0) {
            View itemView = layoutState.next(recycler);
            measureChildWithMargins(itemView, 0, 0);
            int left = getPaddingLeft();
            int right = left + orientationHelper.getDecoratedMeasurementInOther(itemView);
            layoutState.itemConsumed = orientationHelper.getDecoratedMeasurement(itemView);
            int top = layoutState.offSet;
            int bottom = top + layoutState.itemConsumed;
            if (mLayoutScrollDiretaion == LAYOUT_DERATION_TAIL) {
                addView(itemView);
                layoutState.aviableSpace = orientationHelper.getTotalSpace() - layoutState.offSet - layoutState.itemConsumed;
                layoutState.offSet += layoutState.itemConsumed * mLayoutScrollDiretaion;
            } else {
                tryFixGap();
                addView(itemView, 0);
                layoutState.aviableSpace -= layoutState.itemConsumed;
                updateOffset(layoutState.itemConsumed);
            }
            layoutDecoratedWithMargins(itemView, left, top, right, bottom);
            recyclerViewsFromContent(recycler);
        }
        return startValue - layoutState.aviableSpace;
    }

    private void updateOffset(int itemConsumed) {
        if (layoutState.offSet > orientationHelper.getStartAfterPadding()) {
            layoutState.offSet -= Math.min(itemConsumed, layoutState.offSet - orientationHelper.getStartAfterPadding());
        }
    }

    /**
     * 主要是为了修复间距，防止两个Item重叠再一起
     */
    private void tryFixGap() {
        if (getChildCount() > 2) {
            int diff = orientationHelper.getDecoratedEnd(getChildAt(0)) - orientationHelper.getDecoratedStart(getChildAt(1));
            if (diff > 0) {
                offsetChilds(diff);
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
        int absDy = Math.abs(dy);
        udpateLayoutState(absDy, state);
        layoutState.scrollDistance += fillContent(recycler, state);
        if (layoutState.scrollDistance < 0) {
            return 0;
        }
        int consumedY = mLayoutScrollDiretaion * Math.min(absDy, Math.abs(layoutState.scrollDistance));
        offsetChilds(-consumedY);
        recyclerViewsFromContent(recycler);
        saveCurrentAnchorInfo();
        return consumedY;
    }

    /**
     * 获取布局的起始锚点，现在本布局只考虑，从上到下的情况
     * 这里进行保存信息主要是考虑到notifyDataSetChanged的时候chongixn
     *
     * @return
     */
    private void saveCurrentAnchorInfo() {
        if (mLayoutScrollDiretaion == LAYOUT_DERATION_HEAD) {
            mAnchor.mAnchorPosition = getPosition(getChildClosestToEnd());
            mAnchor.mAnchorOffset = orientationHelper.getDecoratedStart(getChildClosestToEnd());
        } else {
            mAnchor.mAnchorPosition = getPosition(getChildClosestToStart());
            if (getChildCount() > 2) {
                mAnchor.mAnchorOffset = orientationHelper.getDecoratedStart(getChildAt(1)) - orientationHelper.getDecoratedEnd(getChildAt(0));
            }
        }
    }

    private void offsetChilds(int consumedY) {
        if (mLayoutScrollDiretaion == LAYOUT_DERATION_TAIL) {
            int childCount = getChildCount();
            for (int index = 0; index < childCount; index++) {
                if (index > 1) {
                    orientationHelper.offsetChild(getChildAt(index), consumedY);
                }
                if (index == 1) {
                    View secndView = getChildAt(index);
                    int secondOffset = -mLayoutScrollDiretaion * Math.min(Math.abs(orientationHelper.getStartAfterPadding() - orientationHelper.getDecoratedStart(secndView)), Math.abs(consumedY));
                    orientationHelper.offsetChild(secndView, secondOffset);
                }
            }
        } else {
            for (int index = 0; index < getChildCount(); index++) {
                if (index != 0) {
                    orientationHelper.offsetChild(getChildAt(index), consumedY);
                }
            }
        }

    }

    /**
     * 从界面上把不不可见的ItemView给Recycle掉
     *
     * @param recycler
     */
    private void recyclerViewsFromContent(RecyclerView.Recycler recycler) {
        if (mLayoutScrollDiretaion == LAYOUT_DERATION_TAIL) {
            recyclerViewFromHead(recycler);
        } else {
            recyclerViewsFromTail(recycler);
        }
    }

    /**
     * 从布局的底部回收看不到的View;
     *
     * @param recycler
     */
    private void recyclerViewsFromTail(RecyclerView.Recycler recycler) {
        int endIndex = getChildCount() - 1;
        int startIndex;
        for (startIndex = endIndex; startIndex >= 0; startIndex--) {
            View itemView = getChildAt(startIndex);
            if (orientationHelper.getDecoratedStart(itemView) <= orientationHelper.getEndAfterPadding()) {
                break;
            }
        }
        if (startIndex == endIndex) {
            return;
        }
        //从最后一个位置的View 开始回收
        for (int index = endIndex; index > startIndex; index--) {
            removeAndRecycleView(getChildAt(index), recycler);
        }
    }

    /**
     * 从布局的顶部回收看不到View
     *
     * @param recycler
     */
    private void recyclerViewFromHead(RecyclerView.Recycler recycler) {
        int endIndex = 0;
        for (int index = 0; index < getChildCount(); index++) {
            View childView = getChildAt(index);
            int decordTop = orientationHelper.getDecoratedStart(childView);
            if (decordTop > orientationHelper.getStartAfterPadding()) {
                break;
            }
            endIndex++;
        }
        recyclerViewsInArea(0, endIndex - 1, recycler);
    }

    /**
     * 将ItemView从RecyclerView当中Recycler掉
     *
     * @param beginIndex 要回收的起始位置
     * @param endIndex   结尾位置
     * @param recycler
     */
    private void recyclerViewsInArea(int beginIndex, int endIndex, RecyclerView.Recycler recycler) {
        if (beginIndex == endIndex) {
            return;
        }
        for (int index = beginIndex; index < endIndex; index++) {
            removeAndRecycleView(getChildAt(index), recycler);
        }
    }


    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    /**
     * 记录当前的itemView的position
     */
    private class AnchorInfo {
        public int mAnchorPosition;
        public int mAnchorOffset;
    }


    private class LayoutState {
        /**
         * 布局的起始位置
         */
        public int mCurrentPosition;

        /**
         * 第二个View和第一个View之间的距离，很重要的
         */
        public int secondOffSet;

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
         * 记录用户滚过多少距离的时候需要New出来一个Item
         */
        public int scrollDistance;
        /**
         * 标识下布局完全没有进行滚动的情况
         */
        public static final int NOT_SCROLL_AT_ALL = Integer.MIN_VALUE;
        private List<RecyclerView.ViewHolder> mScrapList;

        View next(RecyclerView.Recycler recycler) {
            if (mScrapList != null) {
                return nextViewFromScrapList();
            }
            final View view = recycler.getViewForPosition(mCurrentPosition);
            mCurrentPosition += mLayoutScrollDiretaion;
            return view;
        }

        private View nextViewFromScrapList() {
            final int size = mScrapList.size();
            for (int i = 0; i < size; i++) {
                final View view = mScrapList.get(i).itemView;
                final RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) view.getLayoutParams();
                if (lp.isItemRemoved()) {
                    continue;
                }
                if (mCurrentPosition == lp.getViewLayoutPosition()) {
                    assignPositionFromScrapList(view);
                    return view;
                }
            }
            return null;
        }

        public boolean hasMore(RecyclerView.State state) {
            return mCurrentPosition >= 0 && mCurrentPosition < state.getItemCount();
        }

        public void reset() {
            layoutState.offSet = 0;
            aviableSpace = orientationHelper.getEndAfterPadding() - layoutState.offSet;
        }
    }

    private void assignPositionFromScrapList(View view) {

    }
}
