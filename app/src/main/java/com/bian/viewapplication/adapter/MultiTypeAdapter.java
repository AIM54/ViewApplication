package com.bian.viewapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.bian.viewapplication.adapter.inter.TypeFactory;
import com.bian.viewapplication.adapter.inter.Visitable;
import com.bian.viewapplication.adapter.viewholder.TypeFactoryForList;
import com.bian.viewapplication.bean.BankInfo;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by yq05481 on 2016/12/30.
 */

public class MultiTypeAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private TypeFactory typeFactory;
    private List<Visitable> models;

    public MultiTypeAdapter(ArrayList<BankInfo> bankInforList) {
        this.models = new ArrayList<>();
        models.addAll(bankInforList);
        this.typeFactory = new TypeFactoryForList();
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return typeFactory.createViewHolder(viewType, itemView);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.setUpView(models.get(position), position, this);
    }

    @Override
    public int getItemCount() {
        if (null == models) {
            return 0;
        }
        return models.size();
    }

    @Override
    public int getItemViewType(int position) {
        return models.get(position).type(typeFactory);
    }
}
