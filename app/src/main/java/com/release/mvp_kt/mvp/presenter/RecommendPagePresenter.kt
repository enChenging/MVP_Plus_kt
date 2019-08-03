package com.release.mvp_kt.mvp.presenter

import com.release.mvp_kt.base.BasePresenter
import com.release.mvp_kt.ext.ext
import com.release.mvp_kt.http.RetrofitHelper
import com.release.mvp_kt.mvp.contract.RecommendPageContract

/**
 * @author Mr.release
 * @create 2019/7/11
 * @Describe
 */
class RecommendPagePresenter : BasePresenter<RecommendPageContract.View>(),
    RecommendPageContract.Presenter {
    override fun requestData(recommendId: String, number: Int,isRefresh :Boolean) {
        RetrofitHelper.recommendService
            .getRecommendData(recommendId, number)
            .ext(mView, scopeProvider!!,!isRefresh) {
                mView?.loadData(it.newslist)
            }
    }
}



