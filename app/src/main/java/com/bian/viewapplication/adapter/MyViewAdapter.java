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
    private List<String> bankInfos;

    public MyViewAdapter(List<String> bankInfos) {
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
        holder.contentTv.setText(bankInfos.get(position));
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
