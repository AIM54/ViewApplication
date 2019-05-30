package com.bian.viewapplication.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bian.viewapplication.R;


public class GridTextAdapter extends RecyclerView.Adapter<GridTextAdapter.TextViewHolder> {

    String[] mDataList;

    public GridTextAdapter(String[] list) {
        this.mDataList = list;
    }

    @NonNull
    @Override
    public TextViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid_text, parent, false);
        return new TextViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TextViewHolder holder, int position) {
        holder.textView.setText(mDataList[position]);
    }

    @Override
    public int getItemCount() {
        return mDataList.length;
    }

    public class TextViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ViewConfiguration viewConfiguration;

        public TextViewHolder(View itemView) {
            super(itemView);
            viewConfiguration=ViewConfiguration.get(itemView.getContext());
            viewConfiguration.getScaledTouchSlop();
            textView = itemView.findViewById(R.id.tv_name);
        }
    }
}
