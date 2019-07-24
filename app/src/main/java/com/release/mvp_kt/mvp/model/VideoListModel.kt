package com.release.mvp_kt.mvp.model

import com.release.mvp_kt.base.BaseModel
import com.release.mvp_kt.constant.Constant
import com.release.mvp_kt.dao.VideoInfo
import com.release.mvp_kt.http.RetrofitHelper
import com.release.mvp_kt.mvp.contract.VideoListContract
import com.release.mvp_kt.rx.SchedulerUtils
import io.reactivex.Flowable
import io.reactivex.Observable

/**
 * @author Mr.release
 * @create 2019/6/26
 * @Describe
 */
class VideoListModel : BaseModel(), VideoListContract.Model {


    override fun requestData(videoId: String, page: Int): Observable<List<VideoInfo>> {
        return RetrofitHelper.newsService.getVideoList(videoId, page * Constant.PAGE / 2)
            .flatMap { stringListMap -> Observable.just<List<VideoInfo>>(stringListMap[videoId]) }
            .compose(SchedulerUtils.ioToMain())
    }
}