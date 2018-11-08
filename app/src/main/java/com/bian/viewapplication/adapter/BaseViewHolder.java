package com.bian.viewapplication.adapter;

/**
 * Created by Administrator on 2018/11/8.
 */

import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {
    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void setUpView(T model, int position, MultiTypeAdapter adapter);
}
