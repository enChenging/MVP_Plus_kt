package com.release.mvp_kt.mvp.model

import com.google.gson.Gson
import com.release.mvp_kt.base.BaseModel
import com.release.mvp_kt.http.RetrofitHelper
import com.release.mvp_kt.mvp.contract.NewsSpacialContract
import com.release.mvp_kt.mvp.model.bean.SpecialInfoBean
import com.release.mvp_kt.rx.SchedulerUtils
import io.reactivex.Observable

/**
 * @author Mr.release
 * @create 2019/6/26
 * @Describe
 */
class NewsSpacialModel : BaseModel(), NewsSpacialContract.Model {

    override fun requestData(specialId: String): Observable<SpecialInfoBean> {
        return RetrofitHelper.newsService.getSpecial(specialId)
            .flatMap {
                stringSpecialInfoBeanMap ->
//                val list = stringSpecialInfoBeanMap[specialId]
//                val i = Gson().toJson(list)
                Observable.just(stringSpecialInfoBeanMap[specialId])
            }
            .compose(SchedulerUtils.ioToMain())
    }
}