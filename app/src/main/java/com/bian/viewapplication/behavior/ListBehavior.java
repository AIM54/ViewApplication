package com.bian.viewapplication.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bian.viewapplication.util.CommonLog;

/**
 * Created by Administrator on 2018/11/2.
 */

public class ListBehavior extends CoordinatorLayout.Behavior<RecyclerView> {
    public ListBehavior() {
    }

    public ListBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, RecyclerView child, View dependency) {
        return dependency instanceof ImageView;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, RecyclerView child, View dependency) {
        CommonLog.i(dependency.getBottom() + "||" + dependency.getTranslationY());
        child.setTranslationY(dependency.getBottom()+dependency.getTranslationY());
        return true;
    }

}

