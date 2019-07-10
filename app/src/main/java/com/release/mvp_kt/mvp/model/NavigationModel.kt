package com.release.mvp_kt.mvp.model

import com.release.mvp_kt.base.BaseModel
import com.release.mvp_kt.http.RetrofitHelper
import com.release.mvp_kt.mvp.contract.NavigationContract
import com.release.mvp_kt.mvp.model.bean.HttpResult
import com.release.mvp_kt.mvp.model.bean.NavigationBean
import io.reactivex.Observable

/**
 * @author Mr.release
 * @create 2019/7/1
 * @Describe
 */
class NavigationModel : BaseModel(), NavigationContract.Model {

    override fun requestNavigationList(): Observable<HttpResult<List<NavigationBean>>> {
        return RetrofitHelper.service.getNavigationList()
    }

}