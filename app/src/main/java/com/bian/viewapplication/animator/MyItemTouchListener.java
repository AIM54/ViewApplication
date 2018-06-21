package com.bian.viewapplication.animator;

import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;

/**
 * Created by Administrator on 2018/6/21.
 */

public class MyItemTouchListener extends RecyclerView.SimpleOnItemTouchListener {
    private VelocityTracker velocityTracker;
    private ViewConfiguration viewConfiguration;
    private GestureDetectorCompat gestureDetectorCompat;
    private RecyclerView mRecyclerView;

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        initRecyclerView(rv);
        return false;
    }

    private void initRecyclerView(RecyclerView rv) {
        mRecyclerView = rv;
        viewConfiguration = ViewConfiguration.get(mRecyclerView.getContext());
        gestureDetectorCompat=new GestureDetectorCompat(mRecyclerView.getContext(),new MyGestureListener());
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        super.onTouchEvent(rv, e);
    }


    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener{

    }
}
