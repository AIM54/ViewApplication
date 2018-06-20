package com.bian.viewapplication.activity;

import android.graphics.Canvas;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.bian.viewapplication.R;
import com.bian.viewapplication.adapter.BankAdapter;
import com.bian.viewapplication.animator.MyItemAnimator;
import com.bian.viewapplication.animator.MyItemTouchHelper;
import com.bian.viewapplication.bean.BankInfo;
import com.bian.viewapplication.util.CommonLog;
import com.bian.viewapplication.util.Contant;

import java.util.ArrayList;

public class RecyclerViewActivity extends AppCompatActivity {
    private RecyclerView testRecyclerView;
    private BankAdapter bankAdapter;
    private ArrayList<BankInfo> bankInfos;
    private Button addButton, deleteButton;
    private int mBankPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        initView();
        testMath();
    }

    private void testMath() {
        double angle = Math.toRadians(45);
        CommonLog.i(String.format("Math.tan(%f):%f", angle, Math.tan(angle)));
        CommonLog.i(Math.sin(Math.toRadians(30)));
    }

    private void initView() {
        addButton = findViewById(R.id.add_button);
        deleteButton = findViewById(R.id.delete_button);
        bankInfos = Contant.getBankInfoList();
        bankAdapter = new BankAdapter(bankInfos);
        testRecyclerView = findViewById(R.id.test_recycler_view);
        testRecyclerView.setItemAnimator(new MyItemAnimator());
        testRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        testRecyclerView.setAdapter(bankAdapter);
        addButton.setOnClickListener(v -> addBankInfo());
        MyItemTouchHelper itemTouchHelper = new MyItemTouchHelper(new ItemOnTouchCallback(MyItemTouchHelper.UP | MyItemTouchHelper.DOWN, MyItemTouchHelper.START | MyItemTouchHelper.END));
        itemTouchHelper.attachToRecyclerView(testRecyclerView);
    }

    public class ItemOnTouchCallback extends MyItemTouchHelper.SimpleCallback {
        public ItemOnTouchCallback(int dragDirs, int swipeDirs) {
            super(dragDirs, swipeDirs);
        }


        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

        }

        @Override
        public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            if (actionState == MyItemTouchHelper.ACTION_STATE_DRAG) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            } else if (actionState == MyItemTouchHelper.ACTION_STATE_SWIPE) {
                CommonLog.i(String.format("dX:%f||dY:%f", dX, dY));
                viewHolder.itemView.scrollTo(-(int) dX, (int) dY);
            }
        }
    }


    private void addBankInfo() {
        String bankName = String.format("第%d傻逼银行", mBankPosition);
        BankInfo bankInfo = new BankInfo(bankName, R.drawable.logo_abc);
        bankAdapter.addItem(0, bankInfo);
        testRecyclerView.scrollToPosition(0);
        mBankPosition++;
    }
}
