package com.bian.viewapplication.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
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
                0 -> skipToViewPage()
                1 -> skipToShapePage()
                2 -> showEditDialog()
            }
        }
    }


    private fun showEditDialog() {
        val shapeIt = Intent(this, ViewPagerActivity::class.java);
        startActivity(shapeIt)
    }



    private fun skipToShapePage() {
        val shapeIt = Intent(this, ShapeActivity::class.java);
        startActivity(shapeIt)
    }

    private fun skipToViewPage() {
        val viewIt = Intent(this, ViewGroupActivity::class.java)
        startActivity(viewIt)
    }

}
