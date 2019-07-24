package com.release.mvp_kt.mvp.contract

import com.release.mvp_kt.base.IModel
import com.release.mvp_kt.base.IPresenter
import com.release.mvp_kt.base.IView
import com.release.mvp_kt.mvp.model.bean.NewslistBean
import com.release.mvp_kt.mvp.model.bean.RecommendPageBean
import io.reactivex.Observable

/**
 * @author Mr.release
 * @create 2019/7/11
 * @Describe
 */
interface  RecommendPageContract {

    interface View : IView {
        fun loadData(data :List<NewslistBean>)
    }

    interface Presenter : IPresenter<View> {
        fun requestData()
    }

    interface Model : IModel {
        fun requestData(): Observable<RecommendPageBean>
    }
}