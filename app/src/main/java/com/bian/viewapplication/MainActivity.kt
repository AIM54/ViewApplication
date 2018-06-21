package com.bian.viewapplication

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bian.viewapplication.activity.NewListActivity
import com.bian.viewapplication.activity.RecyclerViewActivity
import com.bian.viewapplication.activity.RefreshListActivity
import com.bian.viewapplication.activity.ViewGroupActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        listview_bt.setOnClickListener {
            startActivity(Intent(applicationContext, NewListActivity::class.java))
        }
        recycler_bt.setOnClickListener { startActivity(Intent(applicationContext, RecyclerViewActivity::class.java)) }
        vg_button.setOnClickListener { startActivity(Intent(applicationContext,ViewGroupActivity::class.java)) }
        refresh_button.setOnClickListener { startActivity(Intent(applicationContext,RefreshListActivity::class.java)) }
    }
}
