package com.bian.viewapplication.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.bian.viewapplication.R;
import com.bian.viewapplication.activity.base.BaseActivity;
import com.bian.viewapplication.adapter.MyViewAdapter;
import com.bian.viewapplication.view.layout_manager.MyLayoutManger;

import java.util.ArrayList;
import java.util.List;

public class MyLayoutMangerActivity extends BaseActivity {
    private RecyclerView mRecyclerView;
    private MyViewAdapter bankAdapter;
    private Button getChildCountBt;
    private List<String>mStringList;


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
        mStringList=new ArrayList<>(100);
        for (int index=0;index<100;index++){
            mStringList.add("这是第"+index+"张图");
        }
        bankAdapter = new MyViewAdapter(mStringList);
        MyLayoutManger myLayoutManger = new MyLayoutManger();
        mRecyclerView.setLayoutManager(myLayoutManger);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, RecyclerView.VERTICAL));
        mRecyclerView.setAdapter(bankAdapter);
    }
}
