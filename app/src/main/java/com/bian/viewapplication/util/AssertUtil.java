package com.bian.viewapplication.util;

import android.content.res.AssetManager;

import com.bian.viewapplication.ViewApplication;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class AssertUtil {

    public static void copyFromAssert(String src, String dest) {

        File destFile = new File(dest);
        if (!destFile.getParentFile().exists()) {
            destFile.getParentFile().mkdirs();
        }
        if (destFile.exists()) {
            destFile.delete();
        }
        AssetManager assetManager = ViewApplication.mContext.getAssets();
        try {
            InputStream inputStream = assetManager.open(src);
            byte data[] = new byte[1024];
            FileOutputStream fileOutputStream = new FileOutputStream(destFile);
            while (inputStream.read(data) != -1) {
                fileOutputStream.write(data);
            }
            fileOutputStream.flush();
            fileOutputStream.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }

    }

    public static String getText(String path) {
        AssetManager assetManager = ViewApplication.mContext.getAssets();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(assetManager.open(path)));
            StringBuilder stringBuilder = new StringBuilder();
            String temp;
            while ((temp = bufferedReader.readLine()) != null) {
                stringBuilder.append(temp);
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
