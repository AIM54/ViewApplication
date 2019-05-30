package com.bian.viewapplication.adapter.viewholder;

import android.view.View;

import com.bian.viewapplication.R;
import com.bian.viewapplication.adapter.BaseViewHolder;
import com.bian.viewapplication.adapter.inter.TypeFactory;
import com.bian.viewapplication.bean.BankInfo;


/**
 * Created by yq05481 on 2016/12/30.
 */

public class TypeFactoryForList implements TypeFactory {
    public final int TYPE_RESOURCE_ZORE = R.layout.bank_infor_recycler_item;

    @Override
    public int type(BankInfo bankInfo) {
        return TYPE_RESOURCE_ZORE;
    }

    @Override
    public BaseViewHolder createViewHolder(int type, View itemView) {
        if (type == TYPE_RESOURCE_ZORE) {
            return new BankViewHolder(itemView);
        }
        return null;
    }
}
