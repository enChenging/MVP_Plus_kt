package com.release.mvp_kt.utils


import com.release.mvp_kt.R
import java.util.*

/**
 * Created by long on 2016/9/23.
 * 默认背景工厂类
 */
object DefIconFactory {


    private val DEF_ICON_ID = intArrayOf(
        R.drawable.ic_default_1,
        R.drawable.ic_default_2,
        R.drawable.ic_default_3,
        R.drawable.ic_default_4,
        R.drawable.ic_default_5
    )

    private val sRandom = Random()


    fun provideIcon(): Int {
        val index = sRandom.nextInt(DEF_ICON_ID.size)
        return DEF_ICON_ID[index]
    }

}
