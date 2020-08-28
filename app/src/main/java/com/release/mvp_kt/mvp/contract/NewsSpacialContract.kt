package com.release.mvp_kt.mvp.contract

import com.release.mvp_kt.base.IPresenter
import com.release.mvp_kt.base.IView
import com.release.mvp_kt.mvp.model.SpecialInfoBean
import com.release.mvp_kt.ui.adpater.item.SpecialItem


/**
 * @author Mr.release
 * @create 2019/6/26
 * @Describe
 */
interface NewsSpacialContract {

    interface View : IView {

        fun loadHead(data: SpecialInfoBean)

        fun loadData(data: MutableList<SpecialItem>)
    }

    interface Presenter : IPresenter<View> {

        fun requestData(specialId: String)
    }
}