package com.bian.viewapplication.bean;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

public class TakePhotoItem implements Parcelable {
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

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isSelected() {
        return isSelected;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.pointNo);
        dest.writeString(this.pointCode);
        dest.writeString(this.nameCn);
        dest.writeString(this.nameEn);
        dest.writeString(this.imageUrl);
        dest.writeByte(this.isSelected ? (byte) 1 : (byte) 0);
    }

    protected TakePhotoItem(Parcel in) {
        this.pointNo = in.readString();
        this.pointCode = in.readString();
        this.nameCn = in.readString();
        this.nameEn = in.readString();
        this.imageUrl = in.readString();
        this.isSelected = in.readByte() != 0;
    }

    public static final Parcelable.Creator<TakePhotoItem> CREATOR = new Parcelable.Creator<TakePhotoItem>() {
        @Override
        public TakePhotoItem createFromParcel(Parcel source) {
            return new TakePhotoItem(source);
        }

        @Override
        public TakePhotoItem[] newArray(int size) {
            return new TakePhotoItem[size];
        }
    };
}
