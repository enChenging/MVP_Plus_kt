package com.release.mvp_kt.mvp.model

import com.google.gson.Gson
import com.release.mvp_kt.base.BaseModel
import com.release.mvp_kt.http.RetrofitHelper
import com.release.mvp_kt.mvp.contract.NewsDetailContract
import com.release.mvp_kt.mvp.model.bean.NewsDetailInfoBean
import com.release.mvp_kt.mvp.model.bean.NewsInfoBean
import com.release.mvp_kt.rx.SchedulerUtils
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.functions.Function
import org.reactivestreams.Publisher

/**
 * @author Mr.release
 * @create 2019/6/26
 * @Describe
 */
class NewsDetailModel : BaseModel(), NewsDetailContract.Model {

    override fun requestData(newsId: String): Observable<NewsDetailInfoBean> {
        return RetrofitHelper.newsService.getNewsDetail(newsId)
            .flatMap { stringNewsDetailInfoBeanMap ->
//                val list = stringNewsDetailInfoBeanMap[newsId]
//                val i = Gson().toJson(list)
                Observable.just<NewsDetailInfoBean>(stringNewsDetailInfoBeanMap[newsId])//获取NewsDetailInfoBean
            }
            .compose(SchedulerUtils.ioToMain())
    }
}