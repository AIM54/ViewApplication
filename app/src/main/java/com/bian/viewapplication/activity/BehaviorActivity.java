package com.bian.viewapplication.activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bian.viewapplication.R;
import com.bian.viewapplication.adapter.BankAdapter;
import com.bian.viewapplication.bean.BankInfo;
import com.bian.viewapplication.util.Contant;

import java.util.ArrayList;

public class BehaviorActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private BankAdapter bankAdapter;
    private ArrayList<BankInfo> bankInfos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_behavior);
        initData();
        getWindow().getDecorView();
    }


    private void initData() {
        mRecyclerView = findViewById(R.id.recycler_view);
        bankInfos = Contant.getBankInfoList();
        bankAdapter = new BankAdapter(bankInfos);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(bankAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }
}
