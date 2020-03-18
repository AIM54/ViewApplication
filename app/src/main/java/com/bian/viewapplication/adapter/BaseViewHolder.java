package com.bian.viewapplication.adapter;

/**
 * Created by Administrator on 2018/11/8.
 */

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {
    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void setUpView(T model, int position, MultiTypeAdapter adapter);
}
