package com.release.mvp_kt.mvp.presenter

import com.orhanobut.logger.Logger
import com.release.mvp_kt.App
import com.release.mvp_kt.R
import com.release.mvp_kt.base.BasePresenter
import com.release.mvp_kt.dao.VideoInfo
import com.release.mvp_kt.http.exception.ExceptionHandle
import com.release.mvp_kt.mvp.contract.VideoListContract
import com.release.mvp_kt.mvp.model.VideoListModel
import com.release.mvp_kt.utils.NetWorkUtil
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * @author Mr.release
 * @create 2019/6/26
 * @Describe
 */
class VideoListPresenter : BasePresenter<VideoListContract.Model, VideoListContract.View>(),
    VideoListContract.Presenter {

    override fun createModel(): VideoListContract.Model? = VideoListModel()

    override fun requestData(videoId: String, page: Int) {

        mModel?.requestData(videoId, page)
//            ?.`as`(SchedulerUtils.bindLifecycle(mView as LifecycleOwner))
            ?.subscribe(object : Observer<List<VideoInfo>> {

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

                override fun onNext(t: List<VideoInfo>) {
                    Logger.i("VideoList--onNext:$t")
                    mView?.loadData(t)
                }

                override fun onError(t: Throwable) {
                    Logger.e("VideoList--onError:$t")
                    mView?.hideLoading()
                    mView?.showError(ExceptionHandle.handleException(t))
                }
            })
    }
}
