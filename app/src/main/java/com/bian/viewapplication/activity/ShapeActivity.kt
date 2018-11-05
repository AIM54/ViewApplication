package com.bian.viewapplication.activity

import android.content.res.ColorStateList
import android.graphics.Path
import android.graphics.RectF
import android.graphics.drawable.*
import android.graphics.drawable.shapes.ArcShape
import android.graphics.drawable.shapes.PathShape
import android.os.Build
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.bian.viewapplication.R
import com.bian.viewapplication.bean.ViewLoactionBean
import com.bian.viewapplication.dialog.GuideFragment
import com.bian.viewapplication.shape.MyRoundShape
import com.bian.viewapplication.util.CommonLog
import com.bian.viewapplication.util.Util
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_shape.*

class ShapeActivity : AppCompatActivity() {
    var guildeFragment: GuideFragment? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shape)
        initView1()
        initView2()
        initPathView()
        view1.post { showGuildeDialog(view1) }
        testRound()
        testStateDrawable()
        testGradientDrawable()
    }

    private fun testGradientDrawable() {
        val gradientDraweble = GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT,
                intArrayOf(ContextCompat.getColor(this, R.color.lightgreen),
                        ContextCompat.getColor(this, R.color.greenyellow),
                        ContextCompat.getColor(this, R.color.orange)))
        gradientDraweble.cornerRadius = Util.dip2px(this, 25.toFloat()).toFloat()
        setViewBackground(bottom_view1,gradientDraweble)
    }

    private fun testStateDrawable() {
        val stateDrawable = StateListDrawable()
        var drawable2 = GradientDrawable()
        drawable2.setColor(ContextCompat.getColor(this, R.color.darkturquoise))
        drawable2.cornerRadius = Util.dip2px(this, 20.toFloat()).toFloat()
        drawable2.setStroke(Util.dip2px(this, 5.toFloat()), ContextCompat.getColor(this, R.color.plum))
        stateDrawable.addState(intArrayOf(android.R.attr.state_enabled, -android.R.attr.state_pressed), drawable2)
        var drawable1 = GradientDrawable()
        drawable1.setColor(ContextCompat.getColor(this, R.color.chartreuse))
        drawable1.cornerRadius = Util.dip2px(this, 20.toFloat()).toFloat()
        drawable1.setStroke(Util.dip2px(this, 5.toFloat()), ContextCompat.getColor(this, R.color.orange))
        stateDrawable.addState(intArrayOf(android.R.attr.state_enabled, android.R.attr.state_pressed), drawable1)
        setViewBackground(bottom_view, stateDrawable)
        bottom_view.setOnClickListener {
            Toast.makeText(this, "反对某些邪恶的东西", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * 实验Shape
     */
    private fun testRound() {
        val outerRadius = Util.dip2px(this, 40.toFloat()).toFloat()
        val padding = Util.dip2px(this, 20.toFloat()).toFloat()
        val outerRadiusArrays = floatArrayOf(outerRadius, outerRadius, outerRadius, outerRadius,
                outerRadius, outerRadius, outerRadius, outerRadius)
        val innerRadiusArrays = floatArrayOf(outerRadius, outerRadius, outerRadius, outerRadius,
                outerRadius, outerRadius, outerRadius, outerRadius)
        val paddingRectF = RectF(padding, padding, padding, padding)
        val roundRectShape = MyRoundShape(outerRadiusArrays, paddingRectF, innerRadiusArrays, this)
        val shapeDrawable = ShapeDrawable()
        shapeDrawable.shape = roundRectShape
        shapeDrawable.setPadding(Util.dip2px(this, 30.toFloat()), Util.dip2px(this, 30.toFloat()), Util.dip2px(this, 30.toFloat()), Util.dip2px(this, 30.toFloat()))
        setViewBackground(testview, shapeDrawable)
        setViewBackground(textview, shapeDrawable)
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
        shapeDrawable.shape = pathShape
        shapeDrawable.paint.color = ContextCompat.getColor(this, R.color.blueviolet)
        setViewBackground(path_view, shapeDrawable)
    }

    private fun initView2() {
        val myshape = ArcShape(0f, 270f)
        val shapeDrawable = ShapeDrawable()
        shapeDrawable.shape = myshape
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

