package com.bian.viewapplication.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bian.viewapplication.R;
import com.bian.viewapplication.bean.BankInfo;
import com.bian.viewapplication.util.CommonLog;

import java.util.ArrayList;


/**
 * Created by Administrator on 2018/5/19.
 */

public class BankAdapter extends RecyclerView.Adapter<BankAdapter.MyViewHolder> {
    private ArrayList<BankInfo> bankInfoArrayList;

    public BankAdapter(ArrayList<BankInfo> bankInfos) {
        this.bankInfoArrayList = bankInfos;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.bank_infor_recycler_item, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(itemView);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        BankInfo bankInfo = bankInfoArrayList.get(position);
        holder.nameTv.setText(bankInfo.bankName);
        holder.logoIv.setImageResource(bankInfo.bankLogo);
    }

    @Override
    public int getItemCount() {
        return bankInfoArrayList.size();
    }

    public void addItem(int index, BankInfo bankInfo) {
        bankInfoArrayList.add(index, bankInfo);
        notifyItemInserted(index);
        CommonLog.i("bankInfoArrayList.size:"+bankInfoArrayList.size());
    }

    public void changeItemContent(int index,String bankInfo){
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView logoIv;
        private TextView nameTv;

        public MyViewHolder(View itemView) {
            super(itemView);
            logoIv = itemView.findViewById(R.id.bank_logo_iv);
            nameTv = itemView.findViewById(R.id.bank_name_tv);
        }
    }
}
