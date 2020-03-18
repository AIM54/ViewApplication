package com.bian.viewapplication.shape;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.shapes.RoundRectShape;

import androidx.annotation.Nullable;

/**
 * Created by Administrator on 2018/8/11.
 */

public class MyRoundShape extends RoundRectShape {
    public MyRoundShape(@Nullable float[] outerRadii, @Nullable RectF inset, @Nullable float[] innerRadii, Context context) {
        super(outerRadii, inset, innerRadii);
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        paint.setShader(new LinearGradient(0, 0, 0, getHeight(), Color.GREEN, Color.YELLOW, Shader.TileMode.REPEAT));
        super.draw(canvas, paint);
    }

    @Override
    protected void onResize(float w, float h) {
        super.onResize(w, h);
    }
}
