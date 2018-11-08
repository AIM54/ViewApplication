package com.bian.viewapplication.adapter.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bian.viewapplication.R;
import com.bian.viewapplication.adapter.BaseViewHolder;
import com.bian.viewapplication.adapter.MultiTypeAdapter;
import com.bian.viewapplication.bean.BankInfo;

/**
 * Created by Administrator on 2018/11/8.
 */

public class BankViewHolder extends BaseViewHolder<BankInfo> {
    private TextView nameTv;
    private ImageView logoIv;

    public BankViewHolder(View itemView) {
        super(itemView);
        logoIv = itemView.findViewById(R.id.bank_logo_iv);
        nameTv = itemView.findViewById(R.id.bank_name_tv);
    }

    @Override
    public void setUpView(BankInfo model, int position, MultiTypeAdapter adapter) {
        nameTv.setText(model.getBankName());
        logoIv.setImageResource(model.getBankLogo());
    }
}
