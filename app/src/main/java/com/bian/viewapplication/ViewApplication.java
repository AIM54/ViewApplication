package com.bian.viewapplication;

import android.app.Application;
import android.content.Context;

public class ViewApplication extends Application {

    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }
}
