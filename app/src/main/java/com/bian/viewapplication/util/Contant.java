package com.bian.viewapplication.util;

import android.os.SystemClock;

import com.bian.viewapplication.R;
import com.bian.viewapplication.bean.BankInfo;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2018/6/19.
 */

public class Contant {
    public static final int TYPE_BANK=110;
    public static ArrayList<BankInfo> getBankInfoList() {
        ArrayList<BankInfo> bankInfos = new ArrayList<>();
        bankInfos.add(new BankInfo("中国工商银行", R.drawable.logo_icbc,1));
        bankInfos.add(new BankInfo("中国银行", R.drawable.logo_boc,1));
        bankInfos.add(new BankInfo("中国农业银行", R.drawable.logo_abc,1));
        bankInfos.add(new BankInfo("中国建设银行", R.drawable.logo_cbc,1));
        bankInfos.add(new BankInfo("交通银行", R.drawable.logo_bcm,1));
        bankInfos.add(new BankInfo("兴业银行", R.drawable.logo_cib,1));
        bankInfos.add(new BankInfo("华夏银行", R.drawable.logo_hxb,1));
        bankInfos.add(new BankInfo("招商银行", R.drawable.logo_cmb,1));
        bankInfos.add(new BankInfo("平安银行", R.drawable.logo_pab,1));
        bankInfos.add(new BankInfo("上海浦东发展银行", R.drawable.logo_spd,1));
        bankInfos.add(new BankInfo("民生银行", R.drawable.logo_cmbc,1));
        bankInfos.add(new BankInfo("中信银行", R.drawable.logo_ccb,1));
        bankInfos.add(new BankInfo("中国光大银行", R.drawable.logo_ceb,1));
        bankInfos.add(new BankInfo("广发银行", R.drawable.logo_cgb,1));
        bankInfos.add(new BankInfo("中国邮政储蓄银行", R.drawable.logo_psb,1));
        return bankInfos;
    }

    private void testThreadPool(){
        ExecutorService executorService= Executors.newSingleThreadScheduledExecutor();
        ((ScheduledExecutorService) executorService).scheduleAtFixedRate(new MyRunnable(),10,10, TimeUnit.SECONDS);
    }
    private class MyRunnable implements Runnable{

        @Override
        public void run() {
            SystemClock.sleep(10000);
            CommonLog.i("jade");
        }
    }
}