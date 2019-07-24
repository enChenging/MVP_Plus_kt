package com.release.mvp_kt.mvp.model

import com.google.gson.Gson
import com.orhanobut.logger.Logger
import com.release.mvp_kt.base.BaseModel
import com.release.mvp_kt.constant.Constant
import com.release.mvp_kt.http.RetrofitHelper
import com.release.mvp_kt.mvp.contract.NewsListContract
import com.release.mvp_kt.mvp.model.bean.NewsInfoBean
import com.release.mvp_kt.rx.SchedulerUtils
import io.reactivex.Observable

/**
 * @author Mr.release
 * @create 2019/6/26
 * @Describe
 */
class NewsListModel : BaseModel(), NewsListContract.Model {

    override fun requestData(newsId: String, page: Int): Observable<NewsInfoBean> {

        val type: String = if (newsId == Constant.HEAD_LINE_NEWS)
            "headline"
        else
            "list"

        return RetrofitHelper.newsService.getImportantNews(type, newsId, page * Constant.PAGE)
            .flatMap { stringListMap ->
//                val list = stringListMap[newsId]
//                val i = Gson().toJson(list)
                Observable.fromIterable(stringListMap[newsId]) }
            .compose(SchedulerUtils.ioToMain())
    }
}