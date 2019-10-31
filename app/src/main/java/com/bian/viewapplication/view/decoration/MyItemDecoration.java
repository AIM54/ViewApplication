package com.bian.viewapplication.view.decoration;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.view.View;

import com.bian.viewapplication.util.CommonLog;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

public class MyItemDecoration extends RecyclerView.ItemDecoration {
    private final Paint.FontMetrics mFontMetrics;
    private final float textHeight;
    private DividerItemDecoration dividerItemDecoration;
    private TextPaint textPaint;
    private Paint mPaint;
    private RecyclerView mRecyclerView;
    private int mDividerHeight;


    public MyItemDecoration() {
        textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(30);
        textPaint.setColor(Color.GREEN);
        mFontMetrics = textPaint.getFontMetrics();
        textHeight = mFontMetrics.bottom - mFontMetrics.top;
        mDividerHeight= (int) (textHeight+30);
    }

    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        setRecyclerView(parent);
        CommonLog.i("onDrawOver");
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        CommonLog.i("onDraw");
        setRecyclerView(parent);
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        layoutManager.getPaddingStart();
    }


    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        setRecyclerView(parent);
    }

    private void setRecyclerView(RecyclerView parent) {
        if (mRecyclerView == null) {
            mRecyclerView = parent;
            mRecyclerView.addOnScrollListener(myOnScrollListener);
        }
    }

    private RecyclerView.OnScrollListener myOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            switch (newState) {
                case RecyclerView.SCROLL_STATE_IDLE:
                    break;
                case RecyclerView.SCROLL_STATE_SETTLING:
                    break;
                case RecyclerView.SCROLL_STATE_DRAGGING:
                    break;
            }
        }
    };
}
