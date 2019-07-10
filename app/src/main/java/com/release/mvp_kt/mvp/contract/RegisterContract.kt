package com.release.mvp_kt.mvp.contract

import com.release.mvp_kt.base.IModel
import com.release.mvp_kt.base.IPresenter
import com.release.mvp_kt.base.IView
import com.release.mvp_kt.mvp.model.bean.HttpResult
import com.release.mvp_kt.mvp.model.bean.LoginData
import io.reactivex.Observable

/**
 * @author Mr.release
 * @create 2019/7/1
 * @Describe
 */
interface RegisterContract {

    interface View : IView {

        fun registerSuccess(data: LoginData)

        fun registerFail()
    }

    interface Presenter : IPresenter<View> {

        fun registerWanAndroid(username: String, password: String, repassword: String)

    }

    interface Model : IModel {
        fun registerWanAndroid(username: String, password: String, repassword: String): Observable<HttpResult<LoginData>>
    }

}