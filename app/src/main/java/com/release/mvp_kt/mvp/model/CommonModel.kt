package com.release.mvp_kt.mvp.model

import com.release.mvp_kt.base.BaseModel
import com.release.mvp_kt.http.RetrofitHelper
import com.release.mvp_kt.mvp.contract.CommonContract
import com.release.mvp_kt.mvp.model.bean.HttpResult
import io.reactivex.Observable

/**
 * @author Mr.release
 * @create 2019/6/25
 * @Describe
 */
open class CommonModel : BaseModel(),CommonContract.Model {
    override fun addCollectArticle(id: Int): Observable<HttpResult<Any>> {
        return RetrofitHelper.service.addCollectArticle(id)
    }

    override fun cancelCollectArticle(id: Int): Observable<HttpResult<Any>> {
        return RetrofitHelper.service.cancelCollectArticle(id)
    }

}