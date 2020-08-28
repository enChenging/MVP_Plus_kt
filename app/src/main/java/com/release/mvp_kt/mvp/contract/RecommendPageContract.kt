package com.release.mvp_kt.mvp.contract

import com.release.mvp_kt.base.IPresenter
import com.release.mvp_kt.base.IView
import com.release.mvp_kt.mvp.model.NewslistBean

/**
 * @author Mr.release
 * @create 2019/7/11
 * @Describe
 */
interface RecommendPageContract {

    interface View : IView {
        fun loadData(data: MutableList<NewslistBean>)
    }

    interface Presenter : IPresenter<View> {
        fun requestData(recommendId: String, number: Int,isRefresh :Boolean)
    }
}