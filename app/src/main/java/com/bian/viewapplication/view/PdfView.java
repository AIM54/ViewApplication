package com.bian.viewapplication.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.pdf.PdfRenderer;
import android.os.Build;
import android.os.ParcelFileDescriptor;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewConfiguration;

import com.bian.viewapplication.util.CommonLog;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.view.ViewCompat;


public class PdfView extends View {
    private Paint mPaint;
    private PdfRenderer mPdfRenderer;
    private int mCurrentPageIndex;
    private Bitmap currentBitMap;

    private Camera mCamera;

    private Matrix mCurrentMatrix;

    private int mCurrentRotateDegree;

    private ValueAnimator rotateAnimator;
    private int mCurrentIndex;

    private int mCurrentPointerId;

    private ThreadPoolExecutor decordThreadPool;
    private int firstPointerId;
    private int secondPointerId;
    private ScaleGestureDetector scaleGestureDetector;
    private Matrix matrix;

    private ViewConfiguration viewConfiguration;


    public PdfView(Context context) {
        this(context, null);
    }

    public PdfView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PdfView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setTextSize(30);
        mPaint.setColor(Color.GREEN);
        mCurrentPageIndex = 0;
        mCamera = new Camera();
        int coreSize = Runtime.getRuntime().availableProcessors();
        decordThreadPool = new ThreadPoolExecutor(coreSize, coreSize, 10, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
        matrix = new Matrix();
        viewConfiguration=ViewConfiguration.get(getContext());
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mCurrentIndex = event.getActionIndex();
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                firstPointerId = event.getPointerId(mCurrentIndex);
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                event.getPointerId(mCurrentIndex);
                CommonLog.i("PointerIdDown:"+event.getPointerId(mCurrentIndex));
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_POINTER_UP:
               int figureUpId= event.getPointerId(mCurrentIndex);
               CommonLog.i("figtureUpId:"+figureUpId);
                break;
            case MotionEvent.ACTION_UP:
                CommonLog.i("ACTION_UP:"+event.getPointerId(mCurrentIndex));
                ViewCompat.postOnAnimation(this,new MyRunnable());
                break;
        }
        return true;
    }

    public class MyRunnable implements Runnable{

        @Override
        public void run() {

        }
    }

    public void setPdfFilePath(String pdfFilePath) {
        long beginTime = System.currentTimeMillis();
        try {
            ParcelFileDescriptor parcelFileDescriptor = ParcelFileDescriptor.open(new File(pdfFilePath), ParcelFileDescriptor.MODE_READ_ONLY);
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                mPdfRenderer = new PdfRenderer(parcelFileDescriptor);
                releaseCurrentMap();
                CommonLog.i("InitDurationTime:" + (System.currentTimeMillis() - beginTime) + "ms");
                invalidate();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void releaseCurrentMap() {
        if (currentBitMap != null) {
            currentBitMap.recycle();
            currentBitMap = null;
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (currentBitMap == null) {
            currentBitMap = getCurrentBitMap();
        }
        mCurrentMatrix = new Matrix();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(currentBitMap, 0, 0, mPaint);
    }

    private Bitmap getCurrentBitMap() {
        Bitmap pageBitMap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (pdfFileIsOk()) {
                long beginTime = System.currentTimeMillis();
                double viewWidth=getWidth();
                double viewHeight=getHeight();
                PdfRenderer.Page pdfPage = mPdfRenderer.openPage(mCurrentPageIndex);
                matrix.postScale((float) (viewWidth/pdfPage.getWidth()), (float) (viewHeight/pdfPage.getHeight()));
                pdfPage.render(pageBitMap, null, matrix, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
                pdfPage.close();
                CommonLog.i("durationTime:" + (System.currentTimeMillis() - beginTime) + "ms");
            }
        }
        return pageBitMap;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private boolean pdfFileIsOk() {
        return mPdfRenderer != null &&
                mCurrentPageIndex < mPdfRenderer.getPageCount() &&
                mCurrentPageIndex >= 0;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (rotateAnimator != null) {
            rotateAnimator.cancel();
        }
        if (mPdfRenderer != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mPdfRenderer.close();
            }
        }
    }

}
