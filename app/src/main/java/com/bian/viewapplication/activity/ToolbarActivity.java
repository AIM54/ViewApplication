package com.bian.viewapplication.activity;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.bian.viewapplication.R;
import com.bian.viewapplication.database.MySqliteHelper;

public class ToolbarActivity extends AppCompatActivity {
    private Button createTableBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar);
        initView();
        createStudentTable();
    }

    private void initView() {
        createTableBt = findViewById(R.id.bt_create_table);
    }

    private void createStudentTable() {
        MySqliteHelper sqliteHelper = new MySqliteHelper(this, "tomcat.db", 1);
        SQLiteDatabase database = sqliteHelper.getWritableDatabase();
        insertStudentTest(database);
    }

    private void insertStudentTest(SQLiteDatabase database) {
        ContentValues contentValues = new ContentValues();
        for (int index = 0; index < 10; index++) {
            contentValues.clear();
            contentValues.put("_id", index);
            contentValues.put("name", "脑子有病的常星"+index);
            contentValues.put("classId", 250);
            contentValues.put("studentId", index * 234);
            contentValues.put("city", "上海");
            database.insert("student", null, contentValues);
        }

    }

}
