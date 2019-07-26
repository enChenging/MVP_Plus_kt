package com.release.mvp_kt.mvp.contract

import com.release.mvp_kt.base.IPresenter
import com.release.mvp_kt.base.IView
import com.release.mvp_kt.dao.VideoInfo
import io.reactivex.Observable


/**
 * @author Mr.release
 * @create 2019/6/26
 * @Describe
 */
interface VideoListContract {

    interface View : IView {

        fun loadData(data: List<VideoInfo>)
    }

    interface Presenter : IPresenter<View> {

        fun requestData(videoId: String, page: Int)
    }
}