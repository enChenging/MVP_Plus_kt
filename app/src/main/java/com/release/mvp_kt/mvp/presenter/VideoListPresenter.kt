package com.release.mvp_kt.mvp.presenter

import androidx.lifecycle.LifecycleOwner
import com.release.mvp_kt.base.BasePresenter
import com.release.mvp_kt.dao.VideoInfo
import com.release.mvp_kt.http.exception.ExceptionHandle
import com.release.mvp_kt.mvp.contract.VideoListContract
import com.release.mvp_kt.mvp.model.VideoListModel
import com.release.mvp_kt.rx.SchedulerUtils
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

/**
 * @author Mr.release
 * @create 2019/6/26
 * @Describe
 */
class VideoListPresenter : BasePresenter<VideoListContract.Model, VideoListContract.View>(),
    VideoListContract.Presenter {

    override fun createModel(): VideoListContract.Model? = VideoListModel()

    override fun requestData(newsId: String, page: Int) {

        mModel?.requestData(newsId, page)
            ?.`as`(SchedulerUtils.bindLifecycle(mView as LifecycleOwner))
            ?.subscribe(object : Subscriber<List<VideoInfo>> {
                override fun onComplete() {
                    mView?.hideLoading()
                }

                override fun onSubscribe(s: Subscription) {
                    mView?.showLoading()
                }

                override fun onNext(t: List<VideoInfo>) {
                    mView?.loadData(t)
                }

                override fun onError(t: Throwable) {
                    mView?.hideLoading()
                    mView?.showError(ExceptionHandle.handleException(t))
                }


            })
    }
}
