package com.bian.viewapplication.view;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.NestedScrollingParent;
import androidx.core.view.NestedScrollingParent2;
import androidx.core.view.NestedScrollingParentHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Scroller;

import com.bian.viewapplication.util.CommonLog;

/**
 * Created by Administrator on 2018/11/6.
 */

public class NestedScrollLinearLayout extends LinearLayout implements NestedScrollingParent2 {
    private NestedScrollingParentHelper nestedScrollingParentHelper;
    private Scroller mScroller;

    private View mRefreshHeaderView;
    private View mRefreshFooterView;
    private GestureDetector mGestureDetector;

    public void setmRefreshHeaderView(View headerView) {
        this.mRefreshHeaderView = headerView;
        post(()->{
            CommonLog.i(mRefreshHeaderView.getHeight());
            LayoutParams layoutParams= (LayoutParams) mRefreshHeaderView.getLayoutParams();
            layoutParams.topMargin=-mRefreshHeaderView.getMeasuredHeight();
            mRefreshHeaderView.setLayoutParams(layoutParams);
        });
        addView(mRefreshHeaderView,0);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        CommonLog.i("onMeasure");
    }

    public void setmRefreshFooterView(View mRefreshFooterView) {
        this.mRefreshFooterView = mRefreshFooterView;
    }


    /**
     * 判断当前动作是否是下拉刷新
     */
    private boolean isPullToRefresh=false;
    public NestedScrollLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initScroll(context);
    }


    public NestedScrollLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initScroll(context);
    }


    private void initScroll(Context context) {
        nestedScrollingParentHelper = new NestedScrollingParentHelper(this);
        mScroller = new Scroller(context);
        mGestureDetector=new GestureDetector(context,new MyGestureLister());
    }

    public  class MyGestureLister implements GestureDetector.OnGestureListener{

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public void onShowPress(MotionEvent e) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {

        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return false;
        }
    }




    /**
     *
     * @param child  RecyclerView
     * @param target  本View
     * @param axes
     * @param type
     * @return
     */
    @Override
    public boolean onStartNestedScroll(@NonNull View child, @NonNull View target, int axes, int type) {
        if (child instanceof RecyclerView){
           if (((RecyclerView) child).getLayoutManager() instanceof LinearLayoutManager){
             LinearLayoutManager layoutManager= (LinearLayoutManager) ((RecyclerView) child).getLayoutManager();
             if (isRecyclerViewReachTop((RecyclerView) child,layoutManager)){
                 isPullToRefresh=true;
                 return true;
             }else if (isRecyclerViewReachBottom((RecyclerView) child,layoutManager)){
                 isPullToRefresh=false;
                 return true;
             }
           }
        }
        return false;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }

    /**
     * 判断RecyclerView 有没有划到顶部
     * @param child
     * @param layoutManager
     * @return
     */
    private boolean isRecyclerViewReachTop(RecyclerView child, LinearLayoutManager layoutManager) {
       return layoutManager.findFirstCompletelyVisibleItemPosition()==0&&!child.canScrollVertically(-1);
    }
    /**
     * 判断RecyclerView 有没有划到底部
     * @param child
     * @param layoutManager
     * @return
     */
    private boolean isRecyclerViewReachBottom(RecyclerView child, LinearLayoutManager layoutManager) {
       int lastPosition= child.getAdapter().getItemCount()-1;
        return layoutManager.findLastCompletelyVisibleItemPosition()==lastPosition&&!child.canScrollVertically(1);
    }
    @Override
    public void onNestedScrollAccepted(@NonNull View child, @NonNull View target, int axes, int type) {
        nestedScrollingParentHelper.onNestedScrollAccepted(child,this,axes);
    }

    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        if (isPullToRefresh&&dy<0&&getScrollY()>-mRefreshHeaderView.getMeasuredHeight()){
            consumed[1]=dy;
            scrollBy(0,dy);
        }else if (!isPullToRefresh&&dy>0){
            consumed[1]=dy;
            scrollBy(0,dy);
        }

    }
    @Override
    public void onNestedScroll(@NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
    }

    @Override
    public void onStopNestedScroll(@NonNull View target, int type) {
        CommonLog.i("onStopNestedScroll");
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        CommonLog.i("onNestedPreFling");
        return super.onNestedPreFling(target, velocityX, velocityY);
    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        CommonLog.i("onNestedFling");
        return super.onNestedFling(target, velocityX, velocityY, consumed);
    }
}
