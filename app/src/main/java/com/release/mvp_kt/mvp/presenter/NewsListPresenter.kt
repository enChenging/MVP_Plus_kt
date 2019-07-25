package com.release.mvp_kt.mvp.presenter

import com.orhanobut.logger.Logger
import com.release.mvp_kt.App
import com.release.mvp_kt.R
import com.release.mvp_kt.base.BasePresenter
import com.release.mvp_kt.http.exception.ExceptionHandle
import com.release.mvp_kt.mvp.contract.NewsListContract
import com.release.mvp_kt.mvp.model.NewsListModel
import com.release.mvp_kt.mvp.model.bean.NewsInfoBean
import com.release.mvp_kt.ui.adpater.item.NewsMultiItem
import com.release.mvp_kt.utils.NetWorkUtil
import com.release.mvp_kt.utils.NewsUtils
import com.uber.autodispose.autoDisposable
import io.reactivex.ObservableTransformer
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * @author Mr.release
 * @create 2019/6/26
 * @Describe
 */
class NewsListPresenter : BasePresenter<NewsListContract.Model, NewsListContract.View>(), NewsListContract.Presenter {

    override fun createModel(): NewsListContract.Model? = NewsListModel()

    override fun requestData(newsId: String, page: Int) {
        Logger.i("NewsList---newsId:$newsId  page:$page")
        mModel?.requestData(newsId, page)
            ?.filter {
                if (NewsUtils.isAbNews(it))
                    mView?.loadAdData(it)
                !NewsUtils.isAbNews(it)//决定了是否将结果返回给订阅者
            }
            ?.compose(observableTransformer)
            ?.autoDisposable(scopeProvider!!)
            ?.subscribe(object : Observer<List<NewsMultiItem>> {

                override fun onComplete() {
                    mView?.hideLoading()
                }

                override fun onSubscribe(d: Disposable) {
                    mView?.showLoading()
                    if (!NetWorkUtil.isNetworkConnected(App.instance)) {
                        mView?.showDefaultMsg(App.instance.resources.getString(R.string.network_unavailable_tip))
                        onComplete()
                    }
                }

                override fun onNext(t: List<NewsMultiItem>) {
                    Logger.i("NewsList--onNext:$t")
                    mView?.loadData(t)
                }

                override fun onError(t: Throwable) {
                    Logger.e("NewsList--onError:$t")
                    mView?.showError()
                }
            })
    }

    private val observableTransformer = ObservableTransformer<NewsInfoBean, List<NewsMultiItem>> { upstream ->
        upstream.map { newsInfo ->
            if (NewsUtils.isNewsPhotoSet(newsInfo.skipType))
                NewsMultiItem(NewsMultiItem.ITEM_TYPE_PHOTO_SET, newsInfo)
            else
                NewsMultiItem(NewsMultiItem.ITEM_TYPE_NORMAL, newsInfo)
        }.toList().toObservable()
    }

}
