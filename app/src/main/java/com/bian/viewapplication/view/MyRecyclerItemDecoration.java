package com.bian.viewapplication.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ComposePathEffect;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Rect;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.bian.viewapplication.R;
import com.bian.viewapplication.util.CommonLog;

/**
 * Created by Administrator on 2018/7/7.
 */

public class MyRecyclerItemDecoration extends RecyclerView.ItemDecoration {
    private Context mContext;
    public static final int HORIZONTAL = LinearLayout.HORIZONTAL;
    public static final int VERTICAL = LinearLayout.VERTICAL;
    private int mOrientation;
    private int mLineWidth;
    private final Rect mBounds = new Rect();
    private Paint mPaint;
    private float dashArrays[];

    public MyRecyclerItemDecoration(Context context, int orientation, int lineWidth) {
        this.mContext = context;
        this.mOrientation = orientation;
        this.mLineWidth = lineWidth;
        initPaint();
    }

    private void initPaint() {
        dashArrays = new float[]{dpToPx(40), dpToPx(10), dpToPx(20), dpToPx(10)};
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(ContextCompat.getColor(mContext, R.color.chartreuse));
        mPaint.setStrokeWidth(mContext.getResources().getDimensionPixelSize(R.dimen.dp3));
        DashPathEffect dashPathEffect = new DashPathEffect(dashArrays, 0);
        CornerPathEffect cornerPathEffect = new CornerPathEffect(dpToPx(1.5f));
        ComposePathEffect composePathEffect = new ComposePathEffect(dashPathEffect, cornerPathEffect);
        mPaint.setPathEffect(composePathEffect);
    }

    public float dpToPx(float dpValue) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5);
    }


    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (parent.getLayoutManager() == null || mLineWidth <= 0) {
            return;
        }
        if (mOrientation == VERTICAL) {
            drawVertical(c, parent);
        } else {
            drawHorizontal(c, parent);
        }
    }

    private void drawVertical(Canvas canvas, RecyclerView parent) {
        canvas.save();
        final int left;
        final int right;
        //noinspection AndroidLintNewApi - NewApi lint fails to handle overrides.
        if (parent.getClipToPadding()) {
            left = parent.getPaddingLeft();
            right = parent.getWidth() - parent.getPaddingRight();
            canvas.clipRect(left, parent.getPaddingTop(), right,
                    parent.getHeight() - parent.getPaddingBottom());
        } else {
            left = 0;
            right = parent.getWidth();
        }

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            parent.getDecoratedBoundsWithMargins(child, mBounds);
            final int bottom = mBounds.bottom + Math.round(child.getTranslationY());
            final int top = bottom - mLineWidth;
            canvas.drawRect(left, top, right, bottom, mPaint);
        }
        canvas.restore();
    }

    private void drawHorizontal(Canvas canvas, RecyclerView parent) {
        canvas.save();
        final int top;
        final int bottom;
        //noinspection AndroidLintNewApi - NewApi lint fails to handle overrides.
        if (parent.getClipToPadding()) {
            top = parent.getPaddingTop();
            bottom = parent.getHeight() - parent.getPaddingBottom();
            canvas.clipRect(parent.getPaddingLeft(), top,
                    parent.getWidth() - parent.getPaddingRight(), bottom);
        } else {
            top = 0;
            bottom = parent.getHeight();
        }

        final int childCount = parent.getChildCount();
        CommonLog.i("childCount:" + childCount);
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            parent.getLayoutManager().getDecoratedBoundsWithMargins(child, mBounds);
            final int right = mBounds.right + Math.round(child.getTranslationX());
            final int left = right - mLineWidth;
            canvas.drawRect(left, top, right, bottom, mPaint);
        }
        canvas.restore();
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (mOrientation == VERTICAL) {
            outRect.set(0, 0, 0, mLineWidth);
        } else {
            outRect.set(0, 0, mLineWidth, 0);
        }

    }
}
