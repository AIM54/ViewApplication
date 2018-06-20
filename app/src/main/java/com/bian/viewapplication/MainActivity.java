package com.bian.viewapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceView;
import android.widget.Button;
import android.widget.LinearLayout;

import com.bian.viewapplication.activity.ListViewActivity;
import com.bian.viewapplication.activity.RecyclerViewActivity;
import com.bian.viewapplication.util.CommonLog;
import com.bian.viewapplication.view.HorizonalGroup;

public class MainActivity extends AppCompatActivity {
    private SurfaceView mSurfaceView;
    private Button listButton;
    private Button recyclerButton;
    private LinearLayout linearLayout;
    private Button rightButton;
    private HorizonalGroup horizonalGroup;

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
        linearLayout = findViewById(R.id.linear);
        rightButton = findViewById(R.id.right_button);
        horizonalGroup = findViewById(R.id.horizonal);
        horizonalGroup.scrollBy(50,0);
        rightButton.post(() -> {
            CommonLog.i(String.format("linearLayout.getMeasuredWidth:%d,rightButton.getMeasuredWidth:%d", linearLayout.getMeasuredWidth(), rightButton.getMeasuredWidth()));
            CommonLog.i("horizonalGroup.getMeasuredWidth():"+horizonalGroup.getMeasuredWidth());
        });

    }
}
