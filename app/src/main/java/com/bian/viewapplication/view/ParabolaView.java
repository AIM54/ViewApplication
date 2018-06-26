package com.bian.viewapplication.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.bian.viewapplication.R;
import com.bian.viewapplication.util.CommonLog;
import com.bian.viewapplication.util.Util;

/**
 * 加速度位移公是S=vt+at2
 * Created by Administrator on 2018/6/25.
 * 比如y=ax²+bx+c,
 y'=2ax+b
 过点(p,q)的切线为y=(2ap+b)(x-p)+q
 如果没学过求导,则先设过点(p,q)的切线为y=k(x-p)+q
 */

public class ParabolaView extends View {
    private Paint linePaint;
    private double mWidthDps, mHeightDps;
    private int mWidth, mHeight;

    private Path unitPath;
    /**
     * 这个代表弹射物体的初始速度
     */
    private float initialVelocity;
    private float horizontalVelocity;
    private float verticalVelocity;
    private double shootAngle;
    private Path tempParabolaPath;
    /**
     * 重力加速度
     */
    private static final float GRAVITY = 5f;
    private float gravity;

    public ParabolaView(Context context) {
        this(context, null);
    }

    public ParabolaView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setColor(Color.RED);
        linePaint.setStrokeWidth(getResources().getDimension(R.dimen.dp3));
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        mWidthDps = Util.px2dip(getContext(), getMeasuredWidth());
        mHeightDps = Util.px2dip(getContext(), getMeasuredHeight());
        unitPath = new Path();
        initialVelocity = 21f;
        shootAngle = Math.toRadians(-30);
        gravity = GRAVITY / 10;
        horizontalVelocity = (float) (initialVelocity * Math.cos(shootAngle));
        verticalVelocity = (float) (initialVelocity * Math.sin(shootAngle));
        tempParabolaPath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float time;
        long begineTime=System.currentTimeMillis();
        canvas.translate(0, mHeight);
        tempParabolaPath.reset();
        tempParabolaPath.moveTo(0, 0);
        for (int index = 0; index < mWidth; index++) {
            time = index / horizontalVelocity;
            tempParabolaPath.lineTo(index, (float) (verticalVelocity * time + gravity * Math.pow(time, 2) / 2));
        }
        canvas.drawPath(tempParabolaPath, linePaint);
        canvas.drawLine(0, -mHeight, mWidth, -mHeight, linePaint);
        CommonLog.i("SpendTime:"+(System.currentTimeMillis()-begineTime));
    }
}
