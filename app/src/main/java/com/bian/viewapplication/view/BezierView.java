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

/**
 * Created by bianmingliang on 2018/6/26.
 */

public class BezierView extends View {
    private Paint linePaint;
    private int mHeight, mWidth;
    private Path bezierPath;

    public BezierView(Context context) {
        this(context, null);
    }

    public BezierView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    private void initPaint() {
        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setColor(Color.GREEN);
        linePaint.setStrokeWidth(getResources().getDimension(R.dimen.dp3));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mHeight = getMeasuredHeight();
        mWidth = getMeasuredWidth();
        bezierPath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
       long begineTime=System.currentTimeMillis();
        bezierPath.moveTo(0, 0);
        bezierPath.quadTo(mWidth/2,mHeight,mWidth,0);
        canvas.drawPath(bezierPath,linePaint);
        CommonLog.i("SpendTime:"+(System.currentTimeMillis()-begineTime));
    }
}
