package com.release.mvp_kt.mvp.presenter

import androidx.lifecycle.LifecycleOwner
import com.orhanobut.logger.Logger
import com.release.mvp_kt.base.BasePresenter
import com.release.mvp_kt.http.exception.ExceptionHandle
import com.release.mvp_kt.mvp.contract.NewsListContract
import com.release.mvp_kt.mvp.model.NewsListModel
import com.release.mvp_kt.mvp.model.bean.NewsInfoBean
import com.release.mvp_kt.rx.SchedulerUtils
import com.release.mvp_kt.ui.adpater.item.NewsMultiItem
import com.release.mvp_kt.utils.NewsUtils
import io.reactivex.Flowable
import io.reactivex.FlowableTransformer
import io.reactivex.functions.Function
import org.reactivestreams.Publisher
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

/**
 * @author Mr.release
 * @create 2019/6/26
 * @Describe
 */
class NewsListPresenter : BasePresenter<NewsListContract.Model, NewsListContract.View>(), NewsListContract.Presenter {

    override fun createModel(): NewsListContract.Model? = NewsListModel()

    override fun requestData(newsId: String, page: Int) {

        mModel?.requestData(newsId,page)
            ?.filter {
                if (NewsUtils.isAbNews(it))
                    mView?.loadAdData(it)
                 !NewsUtils.isAbNews(it)
            }
            ?.compose(observableTransformer)
//            ?.`as` (SchedulerUtils.bindLifecycle(mView as LifecycleOwner))
            ?.subscribe(object : Subscriber<List<NewsMultiItem>> {
                override fun onComplete() {
                    mView?.hideLoading()
                }

                override fun onSubscribe(s: Subscription) {
                    mView?.showLoading()
                }

                override fun onNext(t: List<NewsMultiItem>) {
                    Logger.i(t.toString())
                    mView?.loadData(t)
                }

                override fun onError(t: Throwable) {
                    mView?.hideLoading()
                    mView?.showError(ExceptionHandle.handleException(t))
                }


            })
    }

    private val observableTransformer =
        FlowableTransformer<NewsInfoBean, List<NewsMultiItem>> { upstream ->
            upstream
                .map(Function<NewsInfoBean, NewsMultiItem> { newsInfo ->
                    if (NewsUtils.isNewsPhotoSet(newsInfo.skipType)) {
                        NewsMultiItem(NewsMultiItem.ITEM_TYPE_PHOTO_SET, newsInfo)
                    } else NewsMultiItem(NewsMultiItem.ITEM_TYPE_NORMAL, newsInfo)
                })
                .toList()
                .toFlowable()
        }

}
