package com.bian.viewapplication.database;

import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bian.viewapplication.ViewApplication;
import com.bian.viewapplication.bean.TakePhotoItem;
import com.bian.viewapplication.util.AssertUtil;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class MyDbManager {

    private volatile static MyDbManager myDbManager;

    private SQLiteDatabase configDataBase;

    public static final String CONFIG_DB = ViewApplication.mContext.getCacheDir() + File.separator + "MyDataBase" + File.separator + "config.db";
    List<TakePhotoItem> takePhotoItems;

    public static MyDbManager getInstance() {
        if (myDbManager == null) {
            synchronized (MyDbManager.class) {
                if (myDbManager == null) {
                    myDbManager = new MyDbManager();
                }
            }
        }
        return myDbManager;
    }

    private MyDbManager() {
        if (!new File(CONFIG_DB).exists()) {
            AssertUtil.copyFromAssert("database/test.db", CONFIG_DB);
        }
        configDataBase = SQLiteDatabase.openOrCreateDatabase(CONFIG_DB, null);
    }


    public List<TakePhotoItem> getPhotoItems() {
        takePhotoItems = new LinkedList<>();
        getItems("database/camera_query.sql");
        getItems("database/camera_queryb.sql");
        return takePhotoItems;
    }

    private void getItems(String sql) {
        Cursor cursor = configDataBase.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            takePhotoItems.add(new TakePhotoItem(cursor));
        }
        cursor.close();
    }
}
