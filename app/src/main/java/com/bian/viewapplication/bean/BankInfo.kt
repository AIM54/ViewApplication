package com.bian.viewapplication.bean

import android.support.annotation.DrawableRes
import com.bian.viewapplication.adapter.inter.TypeFactory
import com.bian.viewapplication.adapter.inter.Visitable

/**
 * Created by Administrator on 2018/5/17.
 */

data class BankInfo(var bankName: String, @param:DrawableRes var bankLogo: Int) :Visitable {

    override fun type(typeFactory: TypeFactory): Int {
      return typeFactory.type(this)
    }
}
