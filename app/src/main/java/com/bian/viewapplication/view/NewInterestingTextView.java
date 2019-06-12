package com.bian.viewapplication.view;

import android.content.Context;
import android.text.SpannableString;
import android.text.style.ClickableSpan;
import android.view.ScaleGestureDetector;
import android.view.View;

import androidx.annotation.NonNull;

public class NewInterestingTextView extends View {
    private ScaleGestureDetector mScaleGesture;

    public NewInterestingTextView(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
    public void setSpannableText(SpannableString spannableText){
    }
}
