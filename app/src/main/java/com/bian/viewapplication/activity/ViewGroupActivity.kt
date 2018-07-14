package com.bian.viewapplication.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

import com.bian.viewapplication.R
import com.bian.viewapplication.util.CommonLog
import kotlinx.android.synthetic.main.activity_view_group.*

class ViewGroupActivity : AppCompatActivity() {
    private var stringList: ArrayList<String>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_group)
        initView()
        if (stringList?.isNotEmpty() == true) {
            CommonLog.i("stringList is NotEmpty")
        } else {
            CommonLog.i("stringList is Empty")
        }
    }

    private fun initView() {
        text1.setOnClickListener { Toast.makeText(applicationContext, "tomcat", Toast.LENGTH_SHORT).show() }
        ship_iv.setOnClickListener { startActivity(Intent(applicationContext, ShapeActivity::class.java)) }
    }
}
