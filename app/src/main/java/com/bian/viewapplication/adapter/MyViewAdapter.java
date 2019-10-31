package com.bian.viewapplication.adapter;

import android.graphics.Color;
import android.graphics.drawable.PaintDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bian.viewapplication.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewAdapter extends RecyclerView.Adapter<MyViewAdapter.MyViewHolder> {
    private List<String> bankInfos;
    private PaintDrawable drawableArray[];

    public MyViewAdapter(List<String> bankInfos) {
        this.bankInfos = bankInfos;
        int colorArrays[] = new int[]{ Color.GREEN, Color.BLUE,Color.GRAY,Color.RED,Color.LTGRAY,Color.YELLOW};
        initBackGroud(colorArrays);
    }

    private void initBackGroud(int[] colorArrays) {
        drawableArray = new PaintDrawable[colorArrays.length];
        for (int index = 0; index < colorArrays.length; index++) {
            PaintDrawable paintDrawable = new PaintDrawable();
            paintDrawable.getPaint().setColor(colorArrays[index]);
            paintDrawable.setCornerRadius(50);
            drawableArray[index] = paintDrawable;
        }
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
        holder.rootLinear.setBackground(drawableArray[position%drawableArray.length]);
    }

    @Override
    public int getItemCount() {
        return bankInfos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView contentTv;
        public LinearLayout rootLinear;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            contentTv = itemView.findViewById(R.id.tv_content);
            rootLinear = itemView.findViewById(R.id.ll_root);
        }
    }
}
