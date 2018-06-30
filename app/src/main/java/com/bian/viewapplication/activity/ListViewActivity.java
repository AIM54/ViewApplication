package com.bian.viewapplication.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ListPopupWindow;
import android.widget.ListView;

import com.bian.viewapplication.R;

public class ListViewActivity extends AppCompatActivity {
    private ListView mListView;
    private EditText editText;
    private ListPopupWindow listPopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        initView();
    }

    private void initView() {
        mListView = findViewById(R.id.listview);
        mListView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
    }
}
