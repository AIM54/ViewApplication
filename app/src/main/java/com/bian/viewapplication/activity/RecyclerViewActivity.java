package com.bian.viewapplication.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.Button;

import com.bian.viewapplication.R;
import com.bian.viewapplication.adapter.BankAdapter;
import com.bian.viewapplication.animator.MyItemAnimator;
import com.bian.viewapplication.animator.MyItemTouchHelper;
import com.bian.viewapplication.bean.BankInfo;
import com.bian.viewapplication.util.CommonLog;
import com.bian.viewapplication.util.Contant;
import com.bian.viewapplication.view.MyRecyclerItemDecoration;

import java.util.ArrayList;

public class RecyclerViewActivity extends AppCompatActivity {
    private RecyclerView testRecyclerView;
    private BankAdapter bankAdapter;
    private ArrayList<BankInfo> bankInfos;
    private Button addButton, deleteButton;
    private int mBankPosition;
    private LinearLayoutManager layoutManager;
   private ItemTouchHelper itemTouchHelper;
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
        testRecyclerView.addItemDecoration(new MyRecyclerItemDecoration(this, LinearLayoutManager.VERTICAL, getResources().getDimensionPixelSize(R.dimen.dp20)));
        addButton.setOnClickListener(v -> addBankInfo());
        MyItemTouchHelper itemTouchHelper = new MyItemTouchHelper(new ItemOnTouchCallback(MyItemTouchHelper.UP | MyItemTouchHelper.DOWN, MyItemTouchHelper.START));
        itemTouchHelper.attachToRecyclerView(testRecyclerView);
        itemTouchHelper.setRightMenuWidth(getResources().getDimension(R.dimen.dp150) * 2);
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
        public void onMoved(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, int fromPos, RecyclerView.ViewHolder target, int toPos, int x, int y) {
            super.onMoved(recyclerView, viewHolder, fromPos, target, toPos, x, y);
            CommonLog.i(String.format("x:%d,y:%d", x, y));
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            CommonLog.i(String.format("viewHolder.itemView.getMeasuredWidth:%d", viewHolder.itemView.getMeasuredWidth()));
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
