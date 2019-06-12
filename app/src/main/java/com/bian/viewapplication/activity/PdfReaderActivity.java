package com.bian.viewapplication.activity;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.widget.ScrollView;

import com.bian.viewapplication.R;
import com.bian.viewapplication.util.CommonLog;
import com.bian.viewapplication.view.PdfView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import androidx.appcompat.app.AppCompatActivity;

public class PdfReaderActivity extends AppCompatActivity {
    AssetManager mAssetManager;
    private String pdfFileName = "alibaba_java_guilde.pdf";
    private File pdfFile;
    private PdfView mainView;

    private PdfDocument pdfDocument;

    private ScrollView scrollView;

    private Drawable loadingDrawable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_reader);
        try {
            initPdfPage();
            initPdfView();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void initPdfPage() throws IOException {
        pdfFile = new File(getExternalCacheDir() + File.separator + pdfFileName);
        if (!pdfFile.exists() || pdfFile.length() <= 0) {
            mAssetManager = getAssets();
            InputStream inputStream = mAssetManager.open("pdf" + File.separator + pdfFileName);
            byte tempData[] = new byte[1024];
            FileOutputStream fileOutputStream = new FileOutputStream(pdfFile);
            while (inputStream.read(tempData) != -1) {
                fileOutputStream.write(tempData);
            }
            fileOutputStream.close();
            inputStream.close();
        }
    }

    private void initPdfView() {
        mainView = findViewById(R.id.pdfv_main);
        mainView.setPdfFilePath(pdfFile.getAbsolutePath());
    }
}
