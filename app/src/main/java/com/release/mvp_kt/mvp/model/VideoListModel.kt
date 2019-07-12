package com.release.mvp_kt.mvp.model

import com.release.mvp_kt.base.BaseModel
import com.release.mvp_kt.dao.VideoInfo
import com.release.mvp_kt.http.RetrofitHelper
import com.release.mvp_kt.mvp.contract.VideoListContract
import com.release.mvp_kt.mvp.model.bean.NewsDetailInfoBean
import io.reactivex.Flowable

/**
 * @author Mr.release
 * @create 2019/6/26
 * @Describe
 */
class VideoListModel : BaseModel(), VideoListContract.Model {

    override fun requestData(newsId: String,page: Int): Flowable<List<VideoInfo>> {
        return RetrofitHelper.getVideoListAPI(newsId,page)
    }
}