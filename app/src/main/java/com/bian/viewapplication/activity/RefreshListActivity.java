package com.bian.viewapplication.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.bian.viewapplication.R;
import com.bian.viewapplication.adapter.BankAdapter;
import com.bian.viewapplication.bean.BankInfo;
import com.bian.viewapplication.util.CommonLog;
import com.bian.viewapplication.util.Contant;
import com.bian.viewapplication.view.NestedScrollLinearLayout;

import java.util.ArrayList;


public class RefreshListActivity extends AppCompatActivity  {
    private RecyclerView mRecyclerView;
    private BankAdapter bankAdapter;
    private ArrayList<BankInfo> bankInfos;
    private NestedScrollLinearLayout nestedScrollLinearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh_list);
        initView();
        BitmapRegionDecoder bitmapRegionDecoder;
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.recycler_view);
        nestedScrollLinearLayout=findViewById(R.id.nsll_root);
        bankInfos = Contant.getBankInfoList();
        bankAdapter = new BankAdapter(bankInfos);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(bankAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        addHeadView();
    }
    private void addHeadView() {
        View headView= LayoutInflater.from(this).inflate(R.layout.v_refresh_header,null);
        nestedScrollLinearLayout.setmRefreshHeaderView(headView);
    }
}
