package com.bian.viewapplication.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

import com.bian.viewapplication.R
import kotlinx.android.synthetic.main.activity_view_group.*

class ViewGroupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_group)
        initView();
    }

    private fun initView() {
        text1.setOnClickListener { Toast.makeText(applicationContext,"tomcat",Toast.LENGTH_SHORT).show() }
    }
}
