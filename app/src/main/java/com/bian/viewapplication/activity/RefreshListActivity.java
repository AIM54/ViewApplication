package com.bian.viewapplication.activity;

import android.os.Bundle;
import android.os.Messenger;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.WindowManager;
import android.widget.Button;

import com.bian.viewapplication.R;
import com.bian.viewapplication.adapter.BankAdapter;
import com.bian.viewapplication.bean.BankInfo;
import com.bian.viewapplication.util.CommonLog;
import com.bian.viewapplication.util.Contant;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;


public class RefreshListActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private BankAdapter bankAdapter;
    private List<BankInfo> bankInfos;
    private Button addButton;
    private Button deleteButton;
    private Button updateButton;
    private Random random;
    private int index;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh_list);
        initView();
        CommonLog.i("onCreate()");
        initEvent();
        WindowManager windowManager = getWindowManager();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CommonLog.i("onDestroy");
    }

    private void initView() {
        random=new Random();
        mRecyclerView = findViewById(R.id.recycler_view);
        addButton = findViewById(R.id.bt_insert);
        deleteButton = findViewById(R.id.bt_delete);
        updateButton=findViewById(R.id.bt_update);
        bankInfos =new LinkedList<>();
        bankInfos.add(Contant.getBankInfoList().get(0));
        bankAdapter = new BankAdapter(bankInfos);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(bankAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    private void initEvent() {
        addButton.setOnClickListener(v -> {
            int beforeSize=bankInfos.size();
            int listIndex = random.nextInt( Contant.getBankInfoList().size());
            bankInfos.add(Contant.getBankInfoList().get(listIndex));
            bankAdapter.notifyItemInserted(beforeSize);
            CommonLog.i("mRecyclerView.getChildCount:"+mRecyclerView.getChildCount());
        });
        deleteButton.setOnClickListener(v -> {
            bankInfos.remove(bankInfos.size()-1);
            bankAdapter.notifyItemRemoved(0);
        });
        updateButton.setOnClickListener(v -> {
            index++;
            bankInfos.get(0).setBankName("Android"+index);
            bankAdapter.notifyItemChanged(0);
        });
    }



}
