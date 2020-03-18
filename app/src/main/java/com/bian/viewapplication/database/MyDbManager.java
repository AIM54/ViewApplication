package com.bian.viewapplication.database;

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
        if (takePhotoItems == null || takePhotoItems.isEmpty()) {
            takePhotoItems = new LinkedList<>();
            getItems(AssertUtil.getText("database/camera_query.sql"));
            getItems(AssertUtil.getText("database/camera_queryb.sql"));
        }
        return takePhotoItems;
    }

    private void getItems(String sql) {
        if (configDataBase.isOpen()) {
            Cursor cursor = configDataBase.rawQuery(sql, null);
            while (cursor.moveToNext()) {
                takePhotoItems.add(new TakePhotoItem(cursor));
            }
            cursor.close();
        }
    }
}
