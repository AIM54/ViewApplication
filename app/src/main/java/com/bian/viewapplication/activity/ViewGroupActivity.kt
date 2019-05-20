package com.bian.viewapplication.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.widget.Toast

import com.bian.viewapplication.R
import com.bian.viewapplication.util.CommonLog
import kotlinx.android.synthetic.main.activity_view_group.*

class ViewGroupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_group)
        initView()
    }

    private fun initView() {
        text1.setOnClickListener { nsv_root.requestLayout()}
        val textPaint=text1.paint
        textPaint.strokeWidth=resources.getDimension(R.dimen.dp10)
        textPaint.color=ContextCompat.getColor(this,R.color.crimson)
    }
}
