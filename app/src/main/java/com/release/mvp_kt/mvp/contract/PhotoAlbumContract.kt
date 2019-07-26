package com.release.mvp_kt.mvp.contract

import com.release.mvp_kt.base.IPresenter
import com.release.mvp_kt.base.IView
import com.release.mvp_kt.mvp.model.PhotoSetInfoBean
import io.reactivex.Observable


/**
 * @author Mr.release
 * @create 2019/6/26
 * @Describe
 */
interface PhotoAlbumContract {

    interface View : IView {

        fun loadData(data: PhotoSetInfoBean)
    }

    interface Presenter : IPresenter<View> {

        fun requestData(photoId: String)
    }
}