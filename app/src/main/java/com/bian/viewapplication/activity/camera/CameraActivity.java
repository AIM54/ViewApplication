package com.bian.viewapplication.activity.camera;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.bian.viewapplication.R;
import com.bian.viewapplication.bean.TakePhotoItem;
import com.bian.viewapplication.database.MyDbManager;

import java.util.List;

public class CameraActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        initData();
    }

    private void initData() {
        List<TakePhotoItem> list = MyDbManager.getInstance().getPhotoItems();
    }
}
