package com.release.mvp_kt.mvp.presenter

import androidx.lifecycle.LifecycleOwner
import com.release.mvp_kt.base.BasePresenter
import com.release.mvp_kt.http.exception.ExceptionHandle
import com.release.mvp_kt.mvp.contract.PhotoAlbumContract
import com.release.mvp_kt.mvp.model.PhotoAlbumModel
import com.release.mvp_kt.mvp.model.bean.PhotoSetInfoBean
import com.release.mvp_kt.rx.SchedulerUtils
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

/**
 * @author Mr.release
 * @create 2019/6/26
 * @Describe
 */
class PhotoAlbumPresenter : BasePresenter<PhotoAlbumContract.Model, PhotoAlbumContract.View>(),
    PhotoAlbumContract.Presenter {

    override fun createModel(): PhotoAlbumContract.Model? = PhotoAlbumModel()

    override fun requestData(newsId: String) {

        mModel?.requestData(newsId)

            ?.`as`(SchedulerUtils.bindLifecycle(mView as LifecycleOwner))
            ?.subscribe(object : Subscriber<PhotoSetInfoBean> {
                override fun onComplete() {
                    mView?.hideLoading()
                }

                override fun onSubscribe(s: Subscription) {
                    mView?.showLoading()
                }

                override fun onNext(t: PhotoSetInfoBean) {
                    mView?.loadData(t)
                }

                override fun onError(t: Throwable) {
                    mView?.hideLoading()
                    mView?.showError(ExceptionHandle.handleException(t))
                }


            })
    }
}
