package com.bian.viewapplication.bean

import androidx.annotation.DrawableRes
import com.bian.viewapplication.adapter.inter.TypeFactory
import com.bian.viewapplication.adapter.inter.Visitable

/**
 * Created by Administrator on 2018/5/17.
 */

data class BankInfo(var bankName: String, @param:DrawableRes var bankLogo: Int ,var type: Int? =1) :Visitable {

    override fun type(typeFactory: TypeFactory): Int {
      return typeFactory.type(this)
    }
}
