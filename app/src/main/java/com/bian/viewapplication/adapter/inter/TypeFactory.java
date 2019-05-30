package com.bian.viewapplication.adapter.inter;

import android.view.View;

import com.bian.viewapplication.adapter.BaseViewHolder;
import com.bian.viewapplication.bean.BankInfo;


/**
 * Created by yq05481 on 2016/12/30.
 */

public interface TypeFactory {
    int type(BankInfo bankInfo);
    BaseViewHolder createViewHolder(int type, View itemView);
}
