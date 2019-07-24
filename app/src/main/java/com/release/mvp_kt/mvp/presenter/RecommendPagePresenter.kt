package com.release.mvp_kt.mvp.presenter

import com.orhanobut.logger.Logger
import com.release.mvp_kt.base.BasePresenter
import com.release.mvp_kt.http.exception.ExceptionHandle
import com.release.mvp_kt.mvp.contract.RecommendPageContract
import com.release.mvp_kt.mvp.model.RecommendPageModel
import com.release.mvp_kt.mvp.model.bean.RecommendPageBean
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

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
//            ?.`as` (SchedulerUtils.bindLifecycle(mView as LifecycleOwner))
//            ?.retryWhen(RetryWithDelay())
            ?.subscribe(object : Observer<RecommendPageBean> {

                override fun onSubscribe(d: Disposable) {
                    mView?.showLoading()
                }

                override fun onNext(t: RecommendPageBean) {
                    Logger.i("RecommendPage--onNext: $t")
                    mView?.loadData(t.newslist)
                }

                override fun onError(e: Throwable) {
                    Logger.e("RecommendPage--onError: ${e.message}")
                    mView?.hideLoading()
                    mView?.showError(ExceptionHandle.handleException(e))

                }

                override fun onComplete() {
                    mView?.hideLoading()
                }

            })
    }
}



