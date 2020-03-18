package com.bian.viewapplication.bean;

import android.database.Cursor;

public class TakePhotoItem {
    private String pointNo;
    private String pointCode;
    private String nameCn;
    private String nameEn;

    public TakePhotoItem(Cursor cursor) {
        pointNo = cursor.getString(cursor.getColumnIndex("PointNo"));
        pointCode = cursor.getString(cursor.getColumnIndex("PointCode"));
        nameCn = cursor.getString(cursor.getColumnIndex("NameCn"));
        nameEn = cursor.getString(cursor.getColumnIndex("NameEn"));
    }
}
