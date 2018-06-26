package com.bian.viewapplication.activity

import android.graphics.drawable.PaintDrawable
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat

import com.bian.viewapplication.R
import kotlinx.android.synthetic.main.activity_shape.*

class ShapeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shape)
        initView()
    }

    private fun initView() {
        var paintDrawable = PaintDrawable(ContextCompat.getColor(this, R.color.lawngreen))
        paintDrawable.setPadding(10, 20, 10, 20)
        paintDrawable.setCornerRadius(resources.getDimension(R.dimen.dp20))
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view1.background = paintDrawable
        } else {
            view1.setBackgroundDrawable(paintDrawable)
        }
    }
}
