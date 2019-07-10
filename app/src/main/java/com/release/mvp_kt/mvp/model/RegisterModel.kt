package com.release.mvp_kt.mvp.model

import com.release.mvp_kt.base.BaseModel
import com.release.mvp_kt.http.RetrofitHelper
import com.release.mvp_kt.mvp.contract.RegisterContract
import com.release.mvp_kt.mvp.model.bean.HttpResult
import com.release.mvp_kt.mvp.model.bean.LoginData
import io.reactivex.Observable

/**
 * @author Mr.release
 * @create 2019/7/1
 * @Describe
 */
class RegisterModel : BaseModel(), RegisterContract.Model {

    override fun registerWanAndroid(username: String, password: String, repassword: String): Observable<HttpResult<LoginData>> {
        return RetrofitHelper.service.registerWanAndroid(username, password, repassword)
    }

}