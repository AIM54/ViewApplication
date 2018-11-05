package com.bian.viewapplication

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bian.viewapplication.activity.*
import com.bian.viewapplication.bean.BankInfo
import com.bian.viewapplication.util.CrashHandler
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        testKotlin()
    }

    private fun testKotlin() {
        val bankInfo = BankInfo("反对共产党银行", R.drawable.logo_abc)
        val copy = bankInfo.copy(bankName = "dada")
    }

    private fun initView() {
        CrashHandler.getInstance().init(this)
        listview_bt.setOnClickListener {
            startActivity(Intent(applicationContext, NewListActivity::class.java))
        }
        vg_button.setOnClickListener { startActivity(Intent(applicationContext, ViewGroupActivity::class.java)) }
        refresh_button.setOnClickListener { startActivity(Intent(applicationContext, RefreshListActivity::class.java)) }
        behavior_bt.setOnClickListener { startActivity(Intent(applicationContext, BehaviorActivity::class.java)) }
        bianView.setOnClickListener { startActivity(Intent(applicationContext, ToolbarActivity::class.java)) }
    }
}
