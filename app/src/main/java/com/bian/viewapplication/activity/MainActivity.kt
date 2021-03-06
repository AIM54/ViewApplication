package com.bian.viewapplication.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.app.ActivityCompat
import com.bian.viewapplication.R
import com.bian.viewapplication.activity.base.BaseActivity
import com.bian.viewapplication.activity.camera.CameraActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.security.Permission

class MainActivity : BaseActivity() {
    val CAMERA_CODE = 100;
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
                3 -> testCamera()
            }
        }
    }

    private fun testCamera() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), CAMERA_CODE)
            return
        }
        val shapeIt = Intent(this, CameraActivity::class.java);
        startActivity(shapeIt)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            testCamera()
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
