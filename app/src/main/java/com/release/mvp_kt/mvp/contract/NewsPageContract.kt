package com.release.mvp_kt.mvp.contract

import com.release.mvp_kt.base.IPresenter
import com.release.mvp_kt.base.IView
import com.release.mvp_kt.dao.NewsTypeInfo

/**
 * @author Mr.release
 * @create 2019/7/11
 * @Describe
 */
interface NewsPageContract {

    interface View : IView {
        fun loadData(NewsTypeIfs: MutableList<NewsTypeInfo>?)
    }

    interface Presenter : IPresenter<View> {
        fun requestData()
    }
}