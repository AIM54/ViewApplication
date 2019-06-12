package com.bian.viewapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bian.viewapplication.R;
import com.bian.viewapplication.bean.BankInfo;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewAdapter extends RecyclerView.Adapter<MyViewAdapter.MyViewHolder> {
    private List<BankInfo> bankInfos;

    public MyViewAdapter(List<BankInfo> bankInfos) {
        this.bankInfos = bankInfos;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MyViewHolder myViewHolder = new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_card_layout, parent, false));
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.contentTv.setText("第" + position + "张图片");
    }

    @Override
    public int getItemCount() {
        return bankInfos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView contentTv;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            contentTv = itemView.findViewById(R.id.tv_content);

        }
    }
}
