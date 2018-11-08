package com.bian.viewapplication.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.bian.viewapplication.R;
import com.bian.viewapplication.view.NestedScrollListView;

import java.util.ArrayList;

public class EyepetizerActivity extends AppCompatActivity {
    private NestedScrollListView nestedScrollListView;
    private ArrayList<String> tipsList;
    private ArrayAdapter<String> simpleAdapter;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eyepetizer);
        initData();
        initView();
    }

    private void initData() {
        tipsList = new ArrayList<>();
        for (int index = 0; index < 100; index++) {
            tipsList.add("tomcat" + index);
        }
        simpleAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tipsList);
    }

    private void initView() {
        nestedScrollListView = findViewById(R.id.lv_below);
        nestedScrollListView.setAdapter(simpleAdapter);
    }

}
