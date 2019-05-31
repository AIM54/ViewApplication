package com.bian.viewapplication.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.bian.viewapplication.R
import com.bian.viewapplication.dialog.SimpleDialogFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView();
    }

    private fun initView() {
        var arrayAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, resources.getStringArray(R.array.main_page_function))
        listview?.adapter = arrayAdapter
        listview?.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            when (position) {
                0 -> skipToRefreshPage();
                1 -> skipToListPage()
                2 -> skipToViewPage()
                3 -> skipToShapePage()
                4 -> startActivity(Intent(this, GridManagerActivity::class.java))
                5 -> showEditDialog()
            }
        }
    }

    private fun showEditDialog() {

    }

    private fun skipToListPage() {
        val listIt = Intent(this, ListViewActivity::class.java)
        listIt.putExtra("tomcat","in order to defeat cpc,we need to kill")
        startActivity(listIt)
    }


    private fun skipToShapePage() {
        val shapeIt = Intent(this, ShapeActivity::class.java);
        startActivity(shapeIt)
        supportFragmentManager.executePendingTransactions();
    }

    private fun skipToViewPage() {
        val viewIt = Intent(this, ViewGroupActivity::class.java)
        startActivity(viewIt)
    }

    private fun skipToRefreshPage() {
        val intent = Intent(this, RefreshListActivity::class.java)
        startActivity(intent)
    }
}
