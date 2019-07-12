package com.release.mvp_kt.mvp.model

import com.release.mvp_kt.base.BaseModel
import com.release.mvp_kt.http.RetrofitHelper
import com.release.mvp_kt.mvp.contract.NewsDetailContract
import com.release.mvp_kt.mvp.model.bean.NewsDetailInfoBean
import com.release.mvp_kt.mvp.model.bean.NewsInfoBean
import io.reactivex.Flowable

/**
 * @author Mr.release
 * @create 2019/6/26
 * @Describe
 */
class NewsDetailModel : BaseModel(), NewsDetailContract.Model {

    override fun requestData(newsId: String): Flowable<NewsDetailInfoBean> {
        return RetrofitHelper.getNewsDetailAPI(newsId)
    }
}