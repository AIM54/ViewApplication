package com.bian.viewapplication.activity.camera;

import android.content.Context;
import android.content.res.Configuration;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bian.viewapplication.R;
import com.bian.viewapplication.activity.base.BaseActivity;
import com.bian.viewapplication.bean.TakePhotoItem;
import com.bian.viewapplication.database.MyDbManager;
import com.bian.viewapplication.widget.HorizonTaskItemDecoration;
import com.bian.viewapplication.widget.TaskItemDecoration;

import java.util.List;

public class CameraActivity extends BaseActivity implements View.OnClickListener {
    private RecyclerView mRecyclerView;
    private CheckBox flashCb;
    private ImageView takePhotoIv;
    private List<TakePhotoItem> mList;
    private CameraAdapter mAdapter;
    private CameraType mCurrentCamera;
    private Camera2Fragment camera2Fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        initView();
        setUpCameraPreView();
        initData();

    }

    private void setUpCameraPreView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            beginDectorCamera();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void beginDectorCamera() {
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            for (String cameraId : cameraManager.getCameraIdList()) {
                CameraCharacteristics cameraCharacter = cameraManager.getCameraCharacteristics(cameraId);
                int deviceLevel = cameraCharacter.get(CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL);
                mCurrentCamera = deviceLevel >= CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL_FULL ? CameraType.Camera2 : CameraType.Camera1;
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.rv_task);
        findViewById(R.id.tv_back).setOnClickListener(this);
        findViewById(R.id.iv_take_photo).setOnClickListener(this);
    }

    private void initData() {
        mList = MyDbManager.getInstance().getPhotoItems();
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
            mRecyclerView.addItemDecoration(new HorizonTaskItemDecoration(mList.size(), getResources().getDimensionPixelOffset(R.dimen.dp6), ContextCompat.getColor(this, R.color.colorAppTheme)));
        } else {
            //横屏
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            mRecyclerView.addItemDecoration(new TaskItemDecoration(mList.size(), getResources().getDimensionPixelOffset(R.dimen.dp6), ContextCompat.getColor(this, R.color.colorAppTheme)));
        }
        mAdapter = new CameraAdapter(mList);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setItemClickListener(v -> {
            int postion = (int) v.getTag();
            onItemClicked(postion);
        });
        onItemClicked(0);
    }

    private void onItemClicked(int postion) {
        for (int index = 0; index < mList.size(); index++) {
            mList.get(index).setSelected(postion == index);
        }
        mAdapter.notifyDataSetChanged();
        if (mCurrentCamera == CameraType.Camera2) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                showCamera2(mList.get(postion));
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void showCamera2(TakePhotoItem takePhotoItem) {
        camera2Fragment = (Camera2Fragment) getSupportFragmentManager().findFragmentByTag(Camera2Fragment.class.getSimpleName());
        if (camera2Fragment == null) {
            camera2Fragment = Camera2Fragment.newInstance(takePhotoItem, null);
            getSupportFragmentManager().beginTransaction().add(R.id.fl_container, camera2Fragment, Camera2Fragment.class.getSimpleName()).commit();
        }else{
            camera2Fragment.setCurrentItem(takePhotoItem);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.iv_take_photo:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    camera2Fragment.takePicture();
                }
                break;
        }
    }
}
