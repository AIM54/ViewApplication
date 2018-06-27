package com.bian.viewapplication.activity

import android.graphics.Path
import android.graphics.drawable.Drawable
import android.graphics.drawable.PaintDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.ArcShape
import android.graphics.drawable.shapes.PathShape
import android.os.Build
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.bian.viewapplication.R
import com.bian.viewapplication.bean.ViewLoactionBean
import com.bian.viewapplication.dialog.GuideFragment
import com.bian.viewapplication.util.CommonLog
import kotlinx.android.synthetic.main.activity_shape.*

class ShapeActivity : AppCompatActivity() {
    var guildeFragment: GuideFragment? = null;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shape)
        initView1()
        initView2()
        initPathView()
        view1.post { showGuildeDialog(view1) }
    }

    private fun showGuildeDialog(view1: View?) {
        CommonLog.i("window.decorView.measuredHeight:${window.decorView.measuredHeight}")
        var location = IntArray(2)
        view1?.let {
            val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
            var statusBarHeight = 0
            if (resourceId > 0) {
                statusBarHeight = resources.getDimensionPixelSize(resourceId)
            }
            it.getLocationInWindow(location)
            val viewloactin = ViewLoactionBean(location[1], location[0], view1.measuredWidth, view1.measuredHeight, statusBarHeight)
            guildeFragment = supportFragmentManager.findFragmentByTag(GuideFragment::class.java.simpleName) as GuideFragment?
            if (guildeFragment == null) {
                guildeFragment = GuideFragment.newInstance(location, viewloactin)
                guildeFragment?.let { it.show(supportFragmentManager, GuideFragment::class.java.simpleName) }
            } else {
                guildeFragment?.let { it.setNewLocationBean(viewloactin) }
            }
        }
    }


    private fun initPathView() {
        val testPath = Path()
        val height = resources.getDimension(R.dimen.dp200)
        testPath.quadTo(height / 2, height / 2, height, 0f)
        val pathShape = PathShape(testPath, height, height)
        val shapeDrawable = ShapeDrawable()
        shapeDrawable.shape = pathShape;
        shapeDrawable.paint.color = ContextCompat.getColor(this, R.color.blueviolet)
        setViewBackground(path_view, shapeDrawable)
    }

    private fun initView2() {
        val myshape = ArcShape(0f, 270f)
        val shapeDrawable = ShapeDrawable()
        shapeDrawable.shape = myshape;
        shapeDrawable.paint.color = ContextCompat.getColor(this, R.color.lightgreen);
        setViewBackground(view2, shapeDrawable)
    }

    private fun initView1() {
        var paintDrawable = PaintDrawable(ContextCompat.getColor(this, R.color.lawngreen))
        paintDrawable.setPadding(10, 20, 10, 20)
        paintDrawable.setCornerRadius(resources.getDimension(R.dimen.dp20))
        setViewBackground(view1, paintDrawable)
    }

    private fun setViewBackground(view1: View?, paintDrawable: Drawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view1?.background = paintDrawable
        } else {
            view1?.setBackgroundDrawable(paintDrawable)
        }
    }
}

