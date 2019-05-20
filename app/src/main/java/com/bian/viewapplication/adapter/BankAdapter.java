package com.bian.viewapplication.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bian.viewapplication.R;
import com.bian.viewapplication.bean.BankInfo;
import com.bian.viewapplication.util.CommonLog;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2018/5/19.
 */

public class BankAdapter extends RecyclerView.Adapter<BankAdapter.MyViewHolder> {
    private List<BankInfo> bankInfoArrayList;

    public BankAdapter(List<BankInfo> bankInfos) {
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
        holder.nameTv.setText(bankInfo.getBankName());
        holder.logoIv.setImageResource(bankInfo.getBankLogo());
        holder.rightButton.setOnClickListener(v -> Toast.makeText(holder.itemView.getContext(), String.format("现在点击了:%d个", position), Toast.LENGTH_SHORT).show());
    }

    @Override
    public int getItemCount() {
        return bankInfoArrayList.size();
    }

    public void addItem(int index, BankInfo bankInfo) {
        bankInfoArrayList.add(index, bankInfo);
        notifyItemInserted(index);
        CommonLog.i("bankInfoArrayList.size:" + bankInfoArrayList.size());
    }

    public void changeItemContent(int index, String bankInfo) {
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView logoIv;
        private TextView nameTv;
        public View rootView;
        private Button rightButton;

        public MyViewHolder(View itemView) {
            super(itemView);
            logoIv = itemView.findViewById(R.id.bank_logo_iv);
            nameTv = itemView.findViewById(R.id.bank_name_tv);
            rootView = itemView.findViewById(R.id.root_layout);
            rightButton = itemView.findViewById(R.id.right_button);
        }

        public void onFling(float velocityX, float velocityY) {

        }

        public void onScroll(float distanceX, float distanceY) {

        }
    }
}
