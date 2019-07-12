package com.release.mvp_kt.mvp.model

import com.release.mvp_kt.base.BaseModel
import com.release.mvp_kt.http.RetrofitHelper
import com.release.mvp_kt.mvp.contract.MainContract
import com.release.mvp_kt.mvp.contract.NewsListContract
import com.release.mvp_kt.mvp.model.bean.NewsInfoBean
import io.reactivex.Flowable

/**
 * @author Mr.release
 * @create 2019/6/26
 * @Describe
 */
class NewsListModel : BaseModel(), NewsListContract.Model {

    override fun requestData(newsId: String, page: Int): Flowable<NewsInfoBean> {
        return RetrofitHelper.getImportantNewAPI(newsId, page)
    }
}