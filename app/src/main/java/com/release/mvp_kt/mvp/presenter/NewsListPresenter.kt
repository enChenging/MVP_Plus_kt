package com.release.mvp_kt.mvp.presenter

import com.orhanobut.logger.Logger
import com.release.mvp_kt.api.BaseURL
import com.release.mvp_kt.base.BasePresenter
import com.release.mvp_kt.constant.Constant
import com.release.mvp_kt.ext.ext
import com.release.mvp_kt.http.RetrofitHelper
import com.release.mvp_kt.mvp.contract.NewsListContract
import com.release.mvp_kt.mvp.model.NewsInfoBean
import com.release.mvp_kt.rx.SchedulerUtils
import com.release.mvp_kt.ui.adpater.item.NewsMultiItem
import com.release.mvp_kt.utils.NewsUtils
import io.reactivex.Observable
import io.reactivex.ObservableTransformer

/**
 * @author Mr.release
 * @create 2019/6/26
 * @Describe
 */
class NewsListPresenter : BasePresenter<NewsListContract.View>(), NewsListContract.Presenter {

    override fun requestData(newsId: String, page: Int, isRefresh: Boolean) {

        Logger.i("NewsList---newsId:$newsId  page:$page")
        val type: String = if (newsId == BaseURL.HEAD_LINE_NEWS)
            "headline"
        else
            "list"

        RetrofitHelper.newsService.getImportantNews(type, newsId, page * Constant.PAGE, Constant.PAGE)
            .compose(SchedulerUtils.ioToMain())
            .flatMap { stringListMap ->
                //                val list = stringListMap[newsId]
//                val i = Gson().toJson(list)
                Observable.fromIterable(stringListMap[newsId])
            }
            ?.filter {
                if (NewsUtils.isAbNews(it))
                    mView?.loadAdData(it)
                !NewsUtils.isAbNews(it)//决定了是否将结果返回给订阅者
            }
            ?.compose(observableTransformer)
            ?.ext(mView, scopeProvider!!, page == 0 && !isRefresh) {
                mView?.loadData(it)
            }
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
