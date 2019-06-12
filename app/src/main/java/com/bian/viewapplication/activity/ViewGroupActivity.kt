package com.bian.viewapplication.activity

import android.os.Bundle
import android.view.ViewTreeObserver
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bian.viewapplication.R
import kotlinx.android.synthetic.main.activity_view_group.*

class ViewGroupActivity : AppCompatActivity(), ViewTreeObserver.OnGlobalLayoutListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_group)
        initView()
    }

    private fun initView() {
        text1.setOnClickListener { nsv_root.requestLayout() }
        val textPaint = text1.paint
        textPaint.strokeWidth = resources.getDimension(R.dimen.dp10)
        textPaint.color = ContextCompat.getColor(this, R.color.crimson)
        window.decorView.viewTreeObserver.addOnGlobalLayoutListener(this)
    }

    override fun onGlobalLayout() {
        window.decorView.viewTreeObserver.removeOnGlobalLayoutListener { this }

    }

}
