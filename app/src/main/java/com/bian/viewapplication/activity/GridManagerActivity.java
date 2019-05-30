package com.bian.viewapplication.activity;

import androidx.dynamicanimation.animation.FloatValueHolder;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bian.viewapplication.R;
import com.bian.viewapplication.adapter.GridTextAdapter;

public class GridManagerActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;
    private SpringForce springForce;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_manager);
        String[] items = new String[1000];
        mRecyclerView = findViewById(R.id.rv_main);
        initStringArray(items);
        GridTextAdapter textAdapter=new GridTextAdapter(items);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,3));
        mRecyclerView.setAdapter(textAdapter);
        SpringAnimation springAnimation=new SpringAnimation(new FloatValueHolder());

    }
    private void initStringArray(String[] items) {
        for (int index = 0; index < 1000; index++) {
            items[index] =Integer.toString(index);
        }
    }
}
