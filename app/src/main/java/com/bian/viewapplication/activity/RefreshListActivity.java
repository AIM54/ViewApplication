package com.bian.viewapplication.activity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.view.SurfaceView;
import android.widget.Button;

import com.bian.viewapplication.R;
import com.bian.viewapplication.adapter.BankAdapter;
import com.bian.viewapplication.bean.BankInfo;
import com.bian.viewapplication.util.CommonLog;
import com.bian.viewapplication.util.Contant;

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
    private Button testButton;
    private Button testLayoutButton;
    private Random random;

    private SurfaceView surfaceView;

    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh_list);
        initView();
        CommonLog.i("onCreate()");
        initEvent();
        addListener();
    }


    private void initView() {
        random = new Random();
        mRecyclerView = findViewById(R.id.recycler_view);
        addButton = findViewById(R.id.bt_insert);
        deleteButton = findViewById(R.id.bt_delete);
        updateButton = findViewById(R.id.bt_update);
        testButton = findViewById(R.id.bt_test);
        testLayoutButton = findViewById(R.id.bt_test_layout);
        bankInfos = new LinkedList<>();
        bankInfos.addAll(Contant.getBankInfoList().subList(0, 6));
        bankAdapter = new BankAdapter(bankInfos);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(bankAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        int childCount=mRecyclerView.getChildCount();

    }

    private void initEvent() {
        addButton.setOnClickListener(v -> {
            int beforeSize = bankInfos.size();
            int listIndex = random.nextInt(Contant.getBankInfoList().size());
            bankInfos.add(Contant.getBankInfoList().get(listIndex));
            bankAdapter.notifyItemInserted(beforeSize);
            CommonLog.i("mRecyclerView.getChildCount:" + mRecyclerView.getChildCount());
        });
        deleteButton.setOnClickListener(v -> {
            bankInfos.remove(bankInfos.size() - 1);
            bankAdapter.notifyItemRemoved(0);
        });
        updateButton.setOnClickListener(v -> {
            index++;
            bankInfos.get(0).setBankName("Android" + index);
            bankAdapter.notifyItemChanged(0);
        });

        testButton.setOnClickListener(v -> {
            mRecyclerView.computeVerticalScrollExtent();
            mRecyclerView.computeVerticalScrollOffset();
            mRecyclerView.computeVerticalScrollRange();
        });
        testLayoutButton.setOnClickListener(v -> {
            int previousSize = bankInfos.size();
            bankInfos.add(new BankInfo("djla", R.drawable.logo_abc, 2));
            bankAdapter.notifyItemInserted(previousSize);
        });
    }

    private void addListener() {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                CommonLog.i("recyclerView.getScrollY:" + mRecyclerView.getScrollY());
            }

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                CommonLog.i("mRecyclerView.getScrollY:" + mRecyclerView.getScrollY());
            }
        });

        mRecyclerView.setOnFlingListener(new RecyclerView.OnFlingListener() {
            @Override
            public boolean onFling(int velocityX, int velocityY) {
                return false;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CommonLog.i("onDestroy()");
    }


}
