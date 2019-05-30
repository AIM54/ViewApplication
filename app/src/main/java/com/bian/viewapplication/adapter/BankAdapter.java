package com.bian.viewapplication.adapter;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bian.viewapplication.R;
import com.bian.viewapplication.bean.BankInfo;

import java.util.List;


/**
 * Created by Administrator on 2018/5/19.
 */

public class BankAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<BankInfo> bankInfoArrayList;

    public BankAdapter(List<BankInfo> bankInfos) {
        this.bankInfoArrayList = bankInfos;
    }

    @Override
    public int getItemViewType(int position) {
        return bankInfoArrayList.get(position).getType();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        View itemView;
        switch (viewType) {
            case 1:
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.bank_infor_recycler_item, parent, false);
                viewHolder = new MyViewHolder(itemView);
                break;
            case 2:
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_large_layout, parent, false);
                viewHolder = new TestViewHolder(itemView);
                break;
            default:
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.bank_infor_recycler_item, parent, false);
                viewHolder = new MyViewHolder(itemView);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder){
            BankInfo bankInfo = bankInfoArrayList.get(position);
            MyViewHolder myViewHolder= (MyViewHolder) holder;
            myViewHolder.nameTv.setText(bankInfo.getBankName());
            myViewHolder.logoIv.setImageResource(bankInfo.getBankLogo());
            myViewHolder.rightButton.setOnClickListener(v -> Toast.makeText(holder.itemView.getContext(), String.format("现在点击了:%d个", position), Toast.LENGTH_SHORT).show());
        }

    }

    @Override
    public int getItemCount() {
        return bankInfoArrayList.size();
    }


    public class TestViewHolder extends RecyclerView.ViewHolder {

        public TestViewHolder(View itemView) {
            super(itemView);
        }
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
