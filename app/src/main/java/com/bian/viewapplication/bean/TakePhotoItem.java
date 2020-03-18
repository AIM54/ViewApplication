package com.bian.viewapplication.bean;

import android.database.Cursor;

public class TakePhotoItem {
    private String pointNo;
    private String pointCode;
    private String nameCn;
    private String nameEn;
    private String imageUrl;
    private boolean isSelected;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPointNo() {
        return pointNo;
    }

    public String getPointCode() {
        return pointCode;
    }

    public String getNameCn() {
        return nameCn;
    }

    public String getNameEn() {
        return nameEn;
    }

    public TakePhotoItem(Cursor cursor) {
        pointNo = cursor.getString(cursor.getColumnIndex("PointNo"));
        pointCode = cursor.getString(cursor.getColumnIndex("PointCode"));
        nameCn = cursor.getString(cursor.getColumnIndex("NameCn"));
        nameEn = cursor.getString(cursor.getColumnIndex("NameEn"));
    }


    public boolean isSelected() {
        return isSelected;
    }
}
