package com.release.mvp_kt.mvp.contract

import com.release.mvp_kt.base.IPresenter
import com.release.mvp_kt.base.IView
import com.release.mvp_kt.mvp.model.VideoInfoBean


/**
 * @author Mr.release
 * @create 2019/6/26
 * @Describe
 */
interface VideoListContract {

    interface View : IView {

        fun loadData(data: List<VideoInfoBean>)
    }

    interface Presenter : IPresenter<View> {

        fun requestData(videoId: String, page: Int,isRefresh :Boolean)
    }
}