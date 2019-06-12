package com.bian.viewapplication.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.bian.viewapplication.R;
import com.bian.viewapplication.adapter.BankAdapter;
import com.bian.viewapplication.adapter.MyViewAdapter;
import com.bian.viewapplication.bean.BankInfo;
import com.bian.viewapplication.util.Contant;
import com.bian.viewapplication.view.layout_manager.MyLayoutManger;
import com.bian.viewapplication.view.layout_manager.NewLinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

public class MyLayoutMangerActivity extends AppCompatActivity {
    private List<BankInfo> bankInfos;
    private RecyclerView mRecyclerView;
    private MyViewAdapter bankAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_layout_manger);
        initView();
        initData();
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.recycler_view);
    }

    private void initData() {
        bankInfos = new ArrayList<>();
        for (int index = 0; index < 100; index++) {
            bankInfos.addAll(Contant.getBankInfoList());
        }
        bankAdapter = new MyViewAdapter(bankInfos);
        NewLinearLayoutManager newLinearLayoutManager=new NewLinearLayoutManager(this,mRecyclerView);
        mRecyclerView.setLayoutManager(newLinearLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,RecyclerView.VERTICAL));
        mRecyclerView.setAdapter(bankAdapter);
    }
}
