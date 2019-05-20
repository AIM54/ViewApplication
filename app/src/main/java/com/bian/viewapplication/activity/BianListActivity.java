package com.bian.viewapplication.activity;

import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;

import com.bian.viewapplication.R;

import java.io.FileInputStream;
import java.io.IOException;

public class BianListActivity extends AppCompatActivity {
  private ListView mListView;
  private RecyclerView mRecyclerView;
  private ScrollView scrollView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bian_list);
        mRecyclerView.setNestedScrollingEnabled(false);
        try {
            BitmapRegionDecoder regionDecoder=BitmapRegionDecoder.newInstance(new FileInputStream(""),true);
            BitmapFactory.Options options=new BitmapFactory.Options();
            options.inSampleSize=2;
            regionDecoder.decodeRegion(new Rect(), options);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
