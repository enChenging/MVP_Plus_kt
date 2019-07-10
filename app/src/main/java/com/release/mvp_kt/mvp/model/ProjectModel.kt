package com.release.mvp_kt.mvp.model

import com.release.mvp_kt.base.BaseModel
import com.release.mvp_kt.http.RetrofitHelper
import com.release.mvp_kt.mvp.contract.ProjectContract
import com.release.mvp_kt.mvp.model.bean.HttpResult
import com.release.mvp_kt.mvp.model.bean.ProjectTreeBean
import io.reactivex.Observable

/**
 * @author Mr.release
 * @create 2019/7/1
 * @Describe
 */
class ProjectModel : BaseModel(), ProjectContract.Model {

    override fun requestProjectTree(): Observable<HttpResult<List<ProjectTreeBean>>> {
        return RetrofitHelper.service.getProjectTree()
    }

}