package com.bian.viewapplication.animator;

import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;

import com.bian.viewapplication.util.CommonLog;

/**
 * Created by Administrator on 2018/6/21.
 */

public class MyItemTouchListener extends RecyclerView.SimpleOnItemTouchListener {
    private VelocityTracker mVelocityTracker;
    private ViewConfiguration viewConfiguration;
    private GestureDetectorCompat gestureDetectorCompat;
    private RecyclerView mRecyclerView;
    private float mInitialTouchX;
    private float mInitialTouchY;
    private float mMaxSwipeVelocity;
    private int mActivePointerId;
    private int mSlop;
    /**
     * The unit we are using to track velocity
     */
    private static final int PIXELS_PER_SECOND = 1000;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent event) {
        initRecyclerView(rv);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mActivePointerId = event.getPointerId(0);
                CommonLog.i("mActivePointerId:" + mActivePointerId);
                mInitialTouchX = event.getX();
                mInitialTouchY = event.getY();
                obtainVelocityTracker();
                break;
            case MotionEvent.ACTION_MOVE:
                if (mVelocityTracker != null) {
                    mVelocityTracker.addMovement(event);
                }
                if (shouldSwipe(event)) {
                    gestureDetectorCompat.onTouchEvent(event);
                    return true;
                }
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                CommonLog.i("MotionEvent.ACTION_UP");
                releaseVelocityTracker();
                break;
        }
        return false;
    }

    private boolean shouldSwipe(MotionEvent event) {
        CommonLog.i("mActivePointerId:" + mActivePointerId);
        int pointerId = event.getPointerId(0);
        if (mActivePointerId == pointerId && mRecyclerView.getScrollState() != RecyclerView.SCROLL_STATE_DRAGGING) {
            float eventX = event.getX(mActivePointerId);
            float eventY = event.getY(mActivePointerId);
            float distanceX = Math.abs(eventX - mInitialTouchX);
            float distanceY = Math.abs(eventY - mInitialTouchY);
            if (distanceX > distanceY && distanceX > mSlop) {
                CommonLog.i("distanceX:" + distanceX);
                return true;
            }
        }
        return false;
    }

    void obtainVelocityTracker() {
        if (mVelocityTracker != null) {
            mVelocityTracker.recycle();
        }
        mVelocityTracker = VelocityTracker.obtain();
    }

    private void releaseVelocityTracker() {
        if (mVelocityTracker != null) {
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }

    private void initRecyclerView(RecyclerView rv) {
        mRecyclerView = rv;
        mMaxSwipeVelocity = mRecyclerView.getResources()
                .getDimension(android.support.v7.recyclerview.R.dimen.item_touch_helper_swipe_escape_max_velocity);
        mLayoutManager = mRecyclerView.getLayoutManager();
        viewConfiguration = ViewConfiguration.get(mRecyclerView.getContext());
        mSlop = viewConfiguration.getScaledTouchSlop();
        gestureDetectorCompat = new GestureDetectorCompat(mRecyclerView.getContext(), new MyGestureListener());
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent event) {
        CommonLog.i("onTouchEvent...");
        gestureDetectorCompat.onTouchEvent(event);
    }


    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            CommonLog.i("onDown");
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            super.onLongPress(e);
            CommonLog.i("onLongPress");
        }


        @Override
        public void onShowPress(MotionEvent e) {
            super.onShowPress(e);
            CommonLog.i("onShowPress");
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            CommonLog.i(String.format("onScroll(distanceX:%f,distanceY:%f)", distanceX, distanceY));
            return true;
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            CommonLog.i("onSingleTapUp");
            return super.onSingleTapUp(e);
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            CommonLog.i(String.format("onFling(velocityX:%f,velocityY:%f)", velocityX, velocityY));
            return true;
        }
    }
}
