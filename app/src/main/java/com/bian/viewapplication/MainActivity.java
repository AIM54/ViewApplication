package com.bian.viewapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceView;
import android.widget.Button;

import com.bian.viewapplication.activity.ListViewActivity;

public class MainActivity extends AppCompatActivity {
    private SurfaceView mSurfaceView;
    private Button listButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        listButton.setOnClickListener(v -> new Intent(this, ListViewActivity.class));
    }
}
