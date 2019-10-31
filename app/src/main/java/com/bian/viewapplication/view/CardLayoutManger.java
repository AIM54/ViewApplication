package com.bian.viewapplication.view;

import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

public class CardLayoutManger extends RecyclerView.LayoutManager {
    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }

}
