package com.bian.viewapplication.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import com.bian.viewapplication.R;

/**
 * Created by Administrator on 2018/6/23.
 */

public class SinView extends View {
    private Paint mPaint;
    private int mWidth, mHeight;
    private Path mPath;
    private PathMeasure mPathMeasure;
    private Bitmap shipBitmap;
    private int animalDuration;
    private float beginPostion;
    private Matrix matrix;
    /**
     * 屏幕像素和坐标点的转换比例
     */
    private static final float transferRadio = 1f / 25f;
    float pos[] = new float[2];
    float tan[] = new float[2];

    public SinView(Context context) {
        this(context, null);
    }

    public SinView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.GREEN);
        mPaint.setStrokeWidth(getResources().getDimension(R.dimen.dp3));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        mPath = new Path();
        mPathMeasure = new PathMeasure();
        shipBitmap = getBitmapFromVectorDrawable(getContext(), R.drawable.ic_ship);
        mPath.moveTo(0, 0);
        for (int i = 0; i < mWidth; i++) {
            mPath.lineTo(i, (float) Math.sin(i * transferRadio) / transferRadio);
        }
        mPathMeasure.setPath(mPath, false);
        animalDuration = 5000;
        matrix = new Matrix();
        beginShipJourney(mPathMeasure.getLength());
    }

    private void beginShipJourney(float length) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, length);
        valueAnimator.setDuration(animalDuration);
        valueAnimator.addUpdateListener(animation -> {
            beginPostion = (float) animation.getAnimatedValue();
            invalidate();
        });
        valueAnimator.setRepeatCount(10);
        valueAnimator.setRepeatMode(ValueAnimator.RESTART);
        valueAnimator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.translate(0, mHeight / 2);
        canvas.drawPath(mPath, mPaint);
        matrix.reset();
        float degrees = (float) (Math.atan2(tan[1], tan[0]) * 180.0 / Math.PI);
        mPathMeasure.getPosTan(beginPostion, pos, tan);
        matrix.postRotate(degrees, shipBitmap.getWidth() / 2, shipBitmap.getHeight() / 2);
        matrix.postTranslate(pos[0] - shipBitmap.getWidth() / 2, pos[1] - shipBitmap.getHeight());
        canvas.drawBitmap(shipBitmap, matrix, mPaint);
    }


    public Bitmap getBitmapFromVectorDrawable(Context context, int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawable = (DrawableCompat.wrap(drawable)).mutate();
        }
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

}
