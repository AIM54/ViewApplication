package com.bian.viewapplication.bean

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by Administrator on 2018/6/26.
 */
data class ViewLoactionBean(val viewTopLocation: Int, val viewLeftLocation: Int, val viewWidth: Int, val viewHeight: Int) : Parcelable {
    override fun toString(): String {
        return "ViewLoactionBean(viewTopLocation=$viewTopLocation, viewLeftLocation=$viewLeftLocation, viewWidth=$viewWidth, viewHeight=$viewHeight)"
    }

    constructor(source: Parcel) : this(
            source.readInt(),
            source.readInt(),
            source.readInt(),
            source.readInt()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(viewTopLocation)
        writeInt(viewLeftLocation)
        writeInt(viewWidth)
        writeInt(viewHeight)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<ViewLoactionBean> = object : Parcelable.Creator<ViewLoactionBean> {
            override fun createFromParcel(source: Parcel): ViewLoactionBean = ViewLoactionBean(source)
            override fun newArray(size: Int): Array<ViewLoactionBean?> = arrayOfNulls(size)
        }
    }
}
