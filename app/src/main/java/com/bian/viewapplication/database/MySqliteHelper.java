package com.bian.viewapplication.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.bian.viewapplication.util.CommonLog;

/**
 * Created by Administrator on 2018/10/16.
 */

public class MySqliteHelper extends SQLiteOpenHelper {
    public MySqliteHelper(Context context, String name, int version) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        CommonLog.i("onCreate");
        String createStudentTableSql = "create table student(_id integer primary key autoincrement,name varchar(100), classId integer, studentId integer,city varchar(20))";
        String createClassTableSql = "create table class(classId integer primary key ,className text,headTeacherId integer,gradeId integer)";
        db.execSQL(createStudentTableSql);
        db.execSQL(createClassTableSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
