package com.release.mvp_kt.mvp.model

import com.release.mvp_kt.base.BaseModel
import com.release.mvp_kt.http.RetrofitHelper
import com.release.mvp_kt.mvp.contract.LoginContract
import com.release.mvp_kt.mvp.model.bean.HttpResult
import com.release.mvp_kt.mvp.model.bean.LoginData
import io.reactivex.Observable

/**
 * @author Mr.release
 * @create 2019/6/25
 * @Describe
 */
class LoginModel :BaseModel(),LoginContract.Model{
    override fun loginWanAndroid(username: String, password: String): Observable<HttpResult<LoginData>> {
        return RetrofitHelper.service.loginWanAndroid(username,password)
    }

}