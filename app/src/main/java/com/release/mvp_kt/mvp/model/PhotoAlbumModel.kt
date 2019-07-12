package com.release.mvp_kt.mvp.model

import com.release.mvp_kt.base.BaseModel
import com.release.mvp_kt.http.RetrofitHelper
import com.release.mvp_kt.mvp.contract.PhotoAlbumContract
import com.release.mvp_kt.mvp.model.bean.NewsDetailInfoBean
import com.release.mvp_kt.mvp.model.bean.PhotoSetInfoBean
import io.reactivex.Flowable

/**
 * @author Mr.release
 * @create 2019/6/26
 * @Describe
 */
class PhotoAlbumModel : BaseModel(), PhotoAlbumContract.Model {

    override fun requestData(photoId: String): Flowable<PhotoSetInfoBean> {
        return RetrofitHelper.getPhotoAlbumAPI(photoId)
    }
}