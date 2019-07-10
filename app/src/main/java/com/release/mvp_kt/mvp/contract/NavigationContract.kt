package com.release.mvp_kt.mvp.contract

import com.release.mvp_kt.base.IModel
import com.release.mvp_kt.base.IPresenter
import com.release.mvp_kt.base.IView
import com.release.mvp_kt.mvp.model.bean.HttpResult
import com.release.mvp_kt.mvp.model.bean.NavigationBean
import io.reactivex.Observable

/**
 * @author Mr.release
 * @create 2019/7/1
 * @Describe
 */
interface NavigationContract {

    interface View : IView {
        fun setNavigationData(list: List<NavigationBean>)
    }

    interface Presenter : IPresenter<View> {
        fun requestNavigationList()
    }

    interface Model : IModel {
        fun requestNavigationList(): Observable<HttpResult<List<NavigationBean>>>
    }

}