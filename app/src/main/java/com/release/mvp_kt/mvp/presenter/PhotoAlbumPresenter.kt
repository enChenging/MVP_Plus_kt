package com.release.mvp_kt.mvp.presenter

import com.release.mvp_kt.base.BasePresenter
import com.release.mvp_kt.ext.ext
import com.release.mvp_kt.http.RetrofitHelper
import com.release.mvp_kt.mvp.contract.PhotoAlbumContract
import com.release.mvp_kt.utils.StringUtils

/**
 * @author Mr.release
 * @create 2019/6/26
 * @Describe
 */
class PhotoAlbumPresenter : BasePresenter<PhotoAlbumContract.View>(),
    PhotoAlbumContract.Presenter {

    override fun requestData(photoId: String) {
        RetrofitHelper.newsService.getPhotoAlbum(StringUtils.clipPhotoSetId(photoId))
            .ext(mView, scopeProvider!!) {
                mView?.loadData(it)
            }
    }
}
