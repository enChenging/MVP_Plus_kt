package com.release.mvp_kt.mvp.presenter

import com.release.mvp_kt.base.BasePresenter
import com.release.mvp_kt.http.exception.ExceptionHandle
import com.release.mvp_kt.mvp.contract.NewsSpacialContract
import com.release.mvp_kt.mvp.model.NewsSpacialModel
import com.release.mvp_kt.mvp.model.bean.NewsItemInfoBean
import com.release.mvp_kt.mvp.model.bean.SpecialInfoBean
import com.release.mvp_kt.mvp.model.bean.TopicsBean
import com.release.mvp_kt.ui.adpater.item.SpecialItem
import io.reactivex.Flowable
import io.reactivex.functions.Consumer
import io.reactivex.functions.Function
import org.reactivestreams.Publisher
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import java.util.*

/**
 * @author Mr.release
 * @create 2019/6/26
 * @Describe
 */
class NewsSpacialPresenter : BasePresenter<NewsSpacialContract.Model, NewsSpacialContract.View>(), NewsSpacialContract.Presenter {

    override fun createModel(): NewsSpacialContract.Model? = NewsSpacialModel()

    override fun requestData(newsId: String) {

        mModel?.requestData(newsId)


            ?.flatMap(Function<SpecialInfoBean, Publisher<SpecialItem>> { specialInfoBean ->
                mView?.loadHead(specialInfoBean)
                convertSpecialBeanToItem(specialInfoBean)
            })
            ?.toList()
            ?.toFlowable()
            ?.subscribe(object : Subscriber<List<SpecialItem>> {
                override fun onComplete() {
                    mView?.hideLoading()
                }

                override fun onSubscribe(s: Subscription) {
                    mView?.showLoading()
                }

                override fun onNext(t: List<SpecialItem>) {
                    mView?.loadData(t)
                }

                override fun onError(t: Throwable) {
                    mView?.hideLoading()
                    mView?.showError(ExceptionHandle.handleException(t))
                }


            })
    }

    private fun convertSpecialBeanToItem(specialBean: SpecialInfoBean): Flowable<SpecialItem> {
        // 这边 +1 是接口数据还有个 topicsplus 的字段可能是穿插在 topics 字段列表中间。这里没有处理 topicsplus
        val specialItems = arrayOfNulls<SpecialItem>(specialBean.topics.size + 1)


        return Flowable
            .fromIterable(specialBean.topics)
            // 获取头部
            .doOnNext(Consumer<TopicsBean> { topicsEntity ->
                specialItems[topicsEntity.index - 1] = SpecialItem(
                    true,
                    topicsEntity.index.toString() + "/" + specialItems.size + " " + topicsEntity.tname
                )
            })
            // 排序
            .toSortedList(Comparator<TopicsBean> { o1, o2 -> o1.index - o2.index })
            .toFlowable()
            // 拆分
            .flatMap(object : Function<List<TopicsBean>, Publisher<TopicsBean>> {

                override fun apply(topicsEntities: List<TopicsBean>): Publisher<TopicsBean> {
                    return Flowable.fromIterable(topicsEntities)
                }
            })
            .flatMap(Function<TopicsBean, Publisher<SpecialItem>> { topicsEntity ->
                // 转换并在每个列表项增加头部
                Flowable.fromIterable(topicsEntity.docs)
                    .map(Function<NewsItemInfoBean, SpecialItem> { newsItemInfoBean -> SpecialItem(newsItemInfoBean) })
                    .startWith(specialItems[topicsEntity.index - 1])
            })
    }
}
