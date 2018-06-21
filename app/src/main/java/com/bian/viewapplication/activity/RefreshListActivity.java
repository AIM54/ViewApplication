package com.bian.viewapplication.activity;

import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bian.viewapplication.R;
import com.bian.viewapplication.adapter.BankAdapter;
import com.bian.viewapplication.animator.MyItemTouchListener;
import com.bian.viewapplication.bean.BankInfo;
import com.bian.viewapplication.util.Contant;
import com.bian.viewapplication.view.RefreshContainer;

import java.util.ArrayList;

public class RefreshListActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView mRecyclerView;
    private BankAdapter bankAdapter;
    private ArrayList<BankInfo> bankInfos;
    private RefreshContainer mRefreshContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh_list);
        initView();
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.recycler_view);
        mRefreshContainer = findViewById(R.id.refresh_container);
        bankInfos = Contant.getBankInfoList();
        bankAdapter = new BankAdapter(bankInfos);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(bankAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRecyclerView.addOnItemTouchListener(new MyItemTouchListener());
        mRefreshContainer.setOnRefreshListener(this);
        mRefreshContainer.setRefreshing(true);
        mRefreshContainer.setColorSchemeColors(Color.YELLOW,Color.GREEN,Color.RED);
        mRefreshContainer.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.orange));
        onRefresh();
    }

    @Override
    public void onRefresh() {
        mRecyclerView.postDelayed(() -> {
            mRefreshContainer.setRefreshing(false);
        }, 3000);
    }
}
