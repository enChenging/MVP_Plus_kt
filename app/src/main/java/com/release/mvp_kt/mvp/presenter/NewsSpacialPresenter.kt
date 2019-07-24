package com.release.mvp_kt.mvp.presenter

import com.orhanobut.logger.Logger
import com.release.mvp_kt.App
import com.release.mvp_kt.R
import com.release.mvp_kt.base.BasePresenter
import com.release.mvp_kt.http.exception.ExceptionHandle
import com.release.mvp_kt.mvp.contract.NewsSpacialContract
import com.release.mvp_kt.mvp.model.NewsSpacialModel
import com.release.mvp_kt.mvp.model.bean.PhotoSetInfoBean
import com.release.mvp_kt.mvp.model.bean.SpecialInfoBean
import com.release.mvp_kt.ui.adpater.item.SpecialItem
import com.release.mvp_kt.utils.NetWorkUtil
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

/**
 * @author Mr.release
 * @create 2019/6/26
 * @Describe
 */
class NewsSpacialPresenter : BasePresenter<NewsSpacialContract.Model, NewsSpacialContract.View>(),
    NewsSpacialContract.Presenter {

    override fun createModel(): NewsSpacialContract.Model? = NewsSpacialModel()

    override fun requestData(newsId: String) {

        mModel?.requestData(newsId)
            ?.flatMap { specialInfoBean ->
                mView?.loadHead(specialInfoBean)
                convertSpecialBeanToItem(specialInfoBean)
            }
            ?.toList()
            ?.toObservable()
            ?.subscribe(object : Observer<List<SpecialItem>> {

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

                override fun onNext(t: List<SpecialItem>) {
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

    private fun convertSpecialBeanToItem(specialBean: SpecialInfoBean): Observable<SpecialItem> {
        // 这边 +1 是接口数据还有个 topicsplus 的字段可能是穿插在 topics 字段列表中间。这里没有处理 topicsplus
        val specialItems = arrayOfNulls<SpecialItem>(specialBean.topics.size + 1)

        return Observable
            .fromIterable(specialBean.topics)
            // 获取头部
            .doOnNext { topicsEntity ->
                specialItems[topicsEntity.index - 1] = SpecialItem(
                    true,
                    topicsEntity.index.toString() + "/" + specialItems.size + " " + topicsEntity.tname
                )
            }
            // 排序
            .toSortedList { o1, o2 -> o1.index - o2.index }
            .toObservable()
            // 拆分
            .flatMap { topicsEntities ->
                Observable.fromIterable(topicsEntities)//逐个发射
            }
            .flatMap { topicsEntity ->
                // 转换并在每个列表项增加头部
                Observable.fromIterable(topicsEntity.docs)
                    .map { newsItemInfoBean -> SpecialItem(newsItemInfoBean) }
                    .startWith(specialItems[topicsEntity.index - 1])
            }
    }
}
