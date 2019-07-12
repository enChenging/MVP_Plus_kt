package com.release.mvp_kt.mvp.presenter

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.release.mvp_kt.base.BasePresenter
import com.release.mvp_kt.http.exception.ExceptionHandle
import com.release.mvp_kt.mvp.contract.RecommendPageContract
import com.release.mvp_kt.mvp.model.RecommendPageModel
import com.release.mvp_kt.mvp.model.bean.RecommendPageBean
import com.release.mvp_kt.rx.SchedulerUtils
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

/**
 * @author Mr.release
 * @create 2019/7/11
 * @Describe
 */
class RecommendPagePresenter : BasePresenter<RecommendPageContract.Model, RecommendPageContract.View>(),
    RecommendPageContract.Presenter {

    override fun createModel(): RecommendPageContract.Model? = RecommendPageModel()

    override fun requestData() {
        mModel?.requestData()
            ?.`as` (SchedulerUtils.bindLifecycle(mView as LifecycleOwner))
            ?.subscribe(object : Subscriber<RecommendPageBean> {
                override fun onComplete() {
                    mView?.hideLoading()
                }

                override fun onSubscribe(s: Subscription) {
                    mView?.showLoading()
                }

                override fun onNext(t: RecommendPageBean) {
                    mView?.loadData(t.newslist)
                }

                override fun onError(t: Throwable) {
                    mView?.hideLoading()
                    mView?.showError(ExceptionHandle.handleException(t))
                }


            })

    }
}



