package com.bian.viewapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bian.viewapplication.R;
import com.bian.viewapplication.bean.ItemBean;

import java.util.List;

public class ChangxingAdapter extends RecyclerView.Adapter<ChangxingAdapter.MyViewHolder> {
    private List<ItemBean> mItemList;

    public ChangxingAdapter(List<ItemBean> itemList) {
        this.mItemList = itemList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MyViewHolder myViewHolder = new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_simple_text, parent, false));
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.contentTv.setText(mItemList.get(position).message);
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView contentTv;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            contentTv = itemView.findViewById(R.id.tv_content);
        }
    }
}
