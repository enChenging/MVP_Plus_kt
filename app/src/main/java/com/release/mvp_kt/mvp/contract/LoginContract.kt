package com.release.mvp_kt.mvp.contract

import com.release.mvp_kt.base.IModel
import com.release.mvp_kt.base.IPresenter
import com.release.mvp_kt.base.IView
import com.release.mvp_kt.mvp.model.bean.HttpResult
import com.release.mvp_kt.mvp.model.bean.LoginData
import io.reactivex.Observable

/**
 * @author Mr.release
 * @create 2019/6/25
 * @Describe
 */
interface LoginContract  {

    interface View:IView{
        fun loginSuccess(data: LoginData)
        fun loginFail()
    }

    interface Presenter:IPresenter<View>{
        fun loginWanAndroid(username:String ,password:String)
    }

    interface Model:IModel{
        fun loginWanAndroid(username:String,password:String): Observable<HttpResult<LoginData>>
    }
}