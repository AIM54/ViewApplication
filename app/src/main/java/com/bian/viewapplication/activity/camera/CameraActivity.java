package com.bian.viewapplication.activity.camera;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bian.viewapplication.R;
import com.bian.viewapplication.activity.base.BaseActivity;
import com.bian.viewapplication.bean.TakePhotoItem;
import com.bian.viewapplication.database.MyDbManager;
import com.bian.viewapplication.widget.TaskItemDecoration;

import java.util.List;

public class CameraActivity extends BaseActivity implements View.OnClickListener {
    private RecyclerView mRecyclerView;
    private CheckBox flashCb;
    private ImageView takePhotoIv;
    private List<TakePhotoItem> mList;
    private CameraAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        initView();
        initData();

    }

    private void initView() {
        mRecyclerView = findViewById(R.id.rv_task);
        findViewById(R.id.tv_back).setOnClickListener(this);
    }

    private void initData() {
        mList = MyDbManager.getInstance().getPhotoItems();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new TaskItemDecoration(mList.size(), getResources().getDimensionPixelOffset(R.dimen.dp6), ContextCompat.getColor(this, R.color.colorAppTheme)));
        mAdapter = new CameraAdapter(mList);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_back:
                finish();
                break;
        }
    }
}
