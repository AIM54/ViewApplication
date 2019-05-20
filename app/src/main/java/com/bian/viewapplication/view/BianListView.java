package com.bian.viewapplication.view;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ScrollView;
import android.widget.TextView;

public class BianListView extends AbsListView {
    private RecyclerView mRecyclerView;
    private ScrollView mScrollView;
    public BianListView(Context context) {
        super(context);
    }

    public BianListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BianListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TextView textView=new TextView(context);
        invalidate();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    @Override
    public ListAdapter getAdapter() {
        return null;
    }

    @Override
    public void setSelection(int position) {

    }
}
