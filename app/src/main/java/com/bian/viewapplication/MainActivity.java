package com.bian.viewapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceView;
import android.widget.Button;

import com.bian.viewapplication.activity.ListViewActivity;
import com.bian.viewapplication.activity.RecyclerViewActivity;

public class MainActivity extends AppCompatActivity {
    private SurfaceView mSurfaceView;
    private Button listButton;
    private Button recyclerButton;
    private Button vgButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        listButton = findViewById(R.id.listview_bt);
        listButton.setOnClickListener(v -> startActivity(new Intent(this, ListViewActivity.class)));
        recyclerButton = findViewById(R.id.recycler_bt);
        recyclerButton.setOnClickListener(v -> startActivity(new Intent(this, RecyclerViewActivity.class)));
        vgButton = findViewById(R.id.vg_button);
    }
}
