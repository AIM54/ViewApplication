package com.bian.viewapplication.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.util.AttributeSet;
import android.view.View;

import com.bian.viewapplication.R;
import com.bian.viewapplication.util.CommonLog;

/**
 * Created by bianmingliang on 2018/6/26.
 */

public class BezierView extends View {
    private Paint linePaint, textPaint;
    private int mHeight, mWidth;
    private Path bezierPath;
    private String message;
    private Path textPath;

    public BezierView(Context context) {
        this(context, null);
    }

    public BezierView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
        setLayerType(LAYER_TYPE_SOFTWARE,null);
    }

    private void initPaint() {
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setStyle(Paint.Style.STROKE);
        textPaint.setColor(Color.RED);
        textPaint.setTextSize(getResources().getDimension(R.dimen.sp25));
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
        message = "打败邪恶的政党";
        textPath = new Path();
        textPaint.getTextPath(message, 0, message.length(), mWidth / 2, mHeight / 2, textPath);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        bezierPath.moveTo(0, 0);
        bezierPath.quadTo(mWidth / 2, mHeight, mWidth, 0);
        canvas.drawLine(0, mHeight / 2, mWidth, mHeight / 2, linePaint);
        canvas.drawPath(bezierPath, linePaint);
        canvas.drawPath(textPath, textPaint);
    }
}
