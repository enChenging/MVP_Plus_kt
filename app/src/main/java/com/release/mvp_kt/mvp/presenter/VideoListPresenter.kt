package com.release.mvp_kt.mvp.presenter

import com.release.mvp_kt.base.BasePresenter
import com.release.mvp_kt.constant.Constant
import com.release.mvp_kt.dao.VideoInfo
import com.release.mvp_kt.ext.ext
import com.release.mvp_kt.http.RetrofitHelper
import com.release.mvp_kt.mvp.contract.VideoListContract
import io.reactivex.Observable

/**
 * @author Mr.release
 * @create 2019/6/26
 * @Describe
 */
class VideoListPresenter : BasePresenter<VideoListContract.View>(),
    VideoListContract.Presenter {

    override fun requestData(videoId: String, page: Int) {

        RetrofitHelper.newsService.getVideoList(videoId, page * Constant.PAGE_TEN, Constant.PAGE_TEN)
            .flatMap { stringListMap -> Observable.just<List<VideoInfo>>(stringListMap[videoId]) }
            .ext(mView, scopeProvider!!, page == 0) {
                mView?.loadData(it)
            }
    }
}
