package com.bian.viewapplication.util;

import com.bian.viewapplication.R;
import com.bian.viewapplication.bean.BankInfo;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/6/19.
 */

public class Contant {
    public static final int TYPE_BANK=110;
    public static ArrayList<BankInfo> getBankInfoList() {
        ArrayList<BankInfo> bankInfos = new ArrayList<>();
        bankInfos.add(new BankInfo("中国工商银行", R.drawable.logo_icbc));
        bankInfos.add(new BankInfo("中国银行", R.drawable.logo_boc));
        bankInfos.add(new BankInfo("中国农业银行", R.drawable.logo_abc));
        bankInfos.add(new BankInfo("中国建设银行", R.drawable.logo_cbc));
        bankInfos.add(new BankInfo("交通银行", R.drawable.logo_bcm));
        bankInfos.add(new BankInfo("兴业银行", R.drawable.logo_cib));
        bankInfos.add(new BankInfo("华夏银行", R.drawable.logo_hxb));
        bankInfos.add(new BankInfo("招商银行", R.drawable.logo_cmb));
        bankInfos.add(new BankInfo("平安银行", R.drawable.logo_pab));
        bankInfos.add(new BankInfo("上海浦东发展银行", R.drawable.logo_spd));
        bankInfos.add(new BankInfo("民生银行", R.drawable.logo_cmbc));
        bankInfos.add(new BankInfo("中信银行", R.drawable.logo_ccb));
        bankInfos.add(new BankInfo("中国光大银行", R.drawable.logo_ceb));
        bankInfos.add(new BankInfo("广发银行", R.drawable.logo_cgb));
        bankInfos.add(new BankInfo("中国邮政储蓄银行", R.drawable.logo_psb));
        return bankInfos;
    }
}
