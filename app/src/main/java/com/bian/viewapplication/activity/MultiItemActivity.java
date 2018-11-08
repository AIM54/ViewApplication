package com.bian.viewapplication.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bian.viewapplication.R;
import com.bian.viewapplication.adapter.MultiTypeAdapter;
import com.bian.viewapplication.util.Contant;

public class MultiItemActivity extends AppCompatActivity {
    private RecyclerView mainRv;
    private MultiTypeAdapter multiTypeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_item);
        initView();
    }

    private void initView() {
        mainRv=findViewById(R.id.rv_main);
        mainRv.setLayoutManager(new LinearLayoutManager(this));
        multiTypeAdapter=new MultiTypeAdapter(Contant.getBankInfoList());
        mainRv.setAdapter(multiTypeAdapter);
    }
}
