package com.release.mvp_kt.mvp.model

import com.release.mvp_kt.base.BaseModel
import com.release.mvp_kt.constant.Constant
import com.release.mvp_kt.http.RetrofitHelper
import com.release.mvp_kt.mvp.contract.RecommendPageContract
import com.release.mvp_kt.mvp.model.bean.RecommendPageBean
import io.reactivex.Flowable

/**
 * @author Mr.release
 * @create 2019/7/11
 * @Describe
 */
class RecommendPageModel :BaseModel(),RecommendPageContract.Model {

    override fun requestData(): Flowable<RecommendPageBean> {
        return RetrofitHelper.getRecommendData("4a0090627cf07a50def18da875165740", Constant.PAGE)
    }
}