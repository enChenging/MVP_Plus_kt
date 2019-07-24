package com.release.mvp_kt.mvp.model

import com.release.mvp_kt.base.BaseModel
import com.release.mvp_kt.constant.Constant
import com.release.mvp_kt.http.RetrofitHelper
import com.release.mvp_kt.mvp.contract.RecommendPageContract
import com.release.mvp_kt.mvp.model.bean.RecommendPageBean
import com.release.mvp_kt.rx.SchedulerUtils
import io.reactivex.Observable

/**
 * @author Mr.release
 * @create 2019/7/11
 * @Describe
 */
@Suppress("UNREACHABLE_CODE")
class RecommendPageModel : BaseModel(), RecommendPageContract.Model {

    override fun requestData(): Observable<RecommendPageBean> {

        return RetrofitHelper.recommendService
            .getRecommendData("4a0090627cf07a50def18da875165740", Constant.PAGE)
            .compose(SchedulerUtils.ioToMain())
    }
}