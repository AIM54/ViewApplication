package com.bian.viewapplication.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.bian.viewapplication.R;
import com.bian.viewapplication.adapter.MyViewAdapter;
import com.bian.viewapplication.bean.BankInfo;
import com.bian.viewapplication.util.Contant;
import com.bian.viewapplication.view.layout_manager.MyLayoutManger;
import com.bian.viewapplication.view.layout_manager.MyLinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

public class MyLayoutMangerActivity extends AppCompatActivity {
    private List<BankInfo> bankInfos;
    private RecyclerView mRecyclerView;
    private MyViewAdapter bankAdapter;
    private Button getChildCountBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_layout_manger);
        initView();
        initData();
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.recycler_view);
        getChildCountBt = findViewById(R.id.bt_get_child_count);
        getChildCountBt.setOnClickListener(v -> {
            Toast.makeText(this, "目前RecyclerView当中的ChildView数量为:" + mRecyclerView.getChildCount(), Toast.LENGTH_SHORT).show();
        });
    }

    private void initData() {
        bankInfos = new ArrayList<>();
        bankInfos.addAll(Contant.getBankInfoList());
        bankAdapter = new MyViewAdapter(bankInfos);
        MyLayoutManger myLayoutManger = new MyLayoutManger();
        mRecyclerView.setLayoutManager(myLayoutManger);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, RecyclerView.VERTICAL));
        mRecyclerView.setAdapter(bankAdapter);
    }
}
