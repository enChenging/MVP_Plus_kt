package com.release.mvp_kt.mvp.presenter

import androidx.lifecycle.LifecycleOwner
import com.orhanobut.logger.Logger
import com.release.mvp_kt.App
import com.release.mvp_kt.R
import com.release.mvp_kt.base.BasePresenter
import com.release.mvp_kt.http.exception.ExceptionHandle
import com.release.mvp_kt.mvp.contract.PhotoAlbumContract
import com.release.mvp_kt.mvp.model.PhotoAlbumModel
import com.release.mvp_kt.mvp.model.bean.PhotoSetInfoBean
import com.release.mvp_kt.rx.SchedulerUtils
import com.release.mvp_kt.utils.NetWorkUtil
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

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
            ?.subscribe(object : Observer<PhotoSetInfoBean> {

                override fun onComplete() {
                    mView?.hideLoading()
                }

                override fun onSubscribe(d: Disposable) {
                    mView?.showLoading()

//                    mView?.addDisposable(d)

                    if (!NetWorkUtil.isNetworkConnected(App.instance)) {
                        mView?.showDefaultMsg(App.instance.resources.getString(R.string.network_unavailable_tip))
                        onComplete()
                    }
                }

                override fun onNext(t: PhotoSetInfoBean) {
                    Logger.i("PhotoAlbum--onNext:$t")
                    mView?.loadData(t)
                }

                override fun onError(t: Throwable) {
                    Logger.e("PhotoAlbum--onError:$t")
                    mView?.hideLoading()
                    mView?.showError(ExceptionHandle.handleException(t))
                }
            })
    }
}
