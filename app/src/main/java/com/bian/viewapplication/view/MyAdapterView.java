package com.bian.viewapplication.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;

public class MyAdapterView extends AdapterView {
    public MyAdapterView(Context context) {
        super(context);
    }

    public MyAdapterView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyAdapterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public Adapter getAdapter() {
        return null;
    }

    @Override
    public void setAdapter(Adapter adapter) {

    }

    @Override
    public View getSelectedView() {
        return null;
    }

    @Override
    public void setSelection(int position) {

    }
}
