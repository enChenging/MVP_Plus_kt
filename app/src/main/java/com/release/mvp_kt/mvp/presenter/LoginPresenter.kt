package com.release.mvp_kt.mvp.presenter

import com.release.mvp_kt.base.BasePresenter
import com.release.mvp_kt.ext.ss
import com.release.mvp_kt.mvp.contract.LoginContract
import com.release.mvp_kt.mvp.model.LoginModel

/**
 * @author Mr.release
 * @create 2019/6/25
 * @Describe
 */
class LoginPresenter :BasePresenter<LoginContract.Model,LoginContract.View>(),LoginContract.Presenter{

    override fun createModel(): LoginContract.Model?  = LoginModel()

    override fun loginWanAndroid(username: String, password: String) {
        mModel?.loginWanAndroid(username,password)?.ss(mModel,mView){
            mView?.loginSuccess(it.data)
        }
    }

}