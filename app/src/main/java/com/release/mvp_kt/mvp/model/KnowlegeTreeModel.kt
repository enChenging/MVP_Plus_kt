package com.release.mvp_kt.mvp.model

import com.release.mvp_kt.base.BaseModel
import com.release.mvp_kt.http.RetrofitHelper
import com.release.mvp_kt.mvp.contract.KnowledgeTreeContract
import com.release.mvp_kt.mvp.model.bean.HttpResult
import com.release.mvp_kt.mvp.model.bean.KnowledgeTreeBody
import io.reactivex.Observable

/**
 * @author Mr.release
 * @create 2019/6/29
 * @Describe
 */
class KnowlegeTreeModel :BaseModel(),KnowledgeTreeContract.Model {
    override fun requestKnowledgeTree(): Observable<HttpResult<List<KnowledgeTreeBody>>> {
        return RetrofitHelper.service.getKnowledgeTree()
    }


}