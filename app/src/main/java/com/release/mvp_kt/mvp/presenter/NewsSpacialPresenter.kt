package com.release.mvp_kt.mvp.presenter

import com.release.mvp_kt.base.BasePresenter
import com.release.mvp_kt.ext.ext
import com.release.mvp_kt.http.RetrofitHelper
import com.release.mvp_kt.mvp.contract.NewsSpacialContract
import com.release.mvp_kt.mvp.model.SpecialInfoBean
import com.release.mvp_kt.rx.SchedulerUtils
import com.release.mvp_kt.ui.adpater.item.SpecialItem
import io.reactivex.Observable

/**
 * @author Mr.release
 * @create 2019/6/26
 * @Describe
 */
class NewsSpacialPresenter : BasePresenter<NewsSpacialContract.View>(),
    NewsSpacialContract.Presenter {


    override fun requestData(specialId: String) {

        RetrofitHelper.newsService.getSpecial(specialId)
            .compose(SchedulerUtils.ioToMain())
            .flatMap { stringSpecialInfoBeanMap ->
                //                val list = stringSpecialInfoBeanMap[specialId]
//                val i = Gson().toJson(list)
                Observable.just(stringSpecialInfoBeanMap[specialId])
            }
            ?.flatMap { specialInfoBean ->
                mView?.loadHead(specialInfoBean)
                convertSpecialBeanToItem(specialInfoBean)
            }
            ?.toList()
            ?.toObservable()
            ?.ext(mView, scopeProvider!!) {
                mView?.loadData(it)
            }
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
