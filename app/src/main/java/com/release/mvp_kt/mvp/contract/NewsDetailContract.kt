package com.release.mvp_kt.mvp.contract

import com.release.mvp_kt.base.IPresenter
import com.release.mvp_kt.base.IView
import com.release.mvp_kt.mvp.model.NewsDetailInfoBean


/**
 * @author Mr.release
 * @create 2019/6/26
 * @Describe
 */
interface NewsDetailContract {

    interface View : IView {

        fun loadData(data: NewsDetailInfoBean)
    }

    interface Presenter : IPresenter<View> {

        fun requestData(newsId: String)
    }
}