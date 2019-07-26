package com.release.mvp_kt.mvp.presenter

import com.release.mvp_kt.base.BasePresenter
import com.release.mvp_kt.ext.ext
import com.release.mvp_kt.http.RetrofitHelper
import com.release.mvp_kt.mvp.contract.NewsDetailContract
import com.release.mvp_kt.mvp.model.NewsDetailInfoBean
import com.release.mvp_kt.utils.ListUtils
import io.reactivex.Observable

/**
 * @author Mr.release
 * @create 2019/6/26
 * @Describe
 */
@Suppress("PrivatePropertyName")
class NewsDetailPresenter : BasePresenter<NewsDetailContract.View>(),
    NewsDetailContract.Presenter {

    private val HTML_IMG_TEMPLATE = "<img src=\"http\" />"

    override fun requestData(newsId: String) {

        RetrofitHelper.newsService.getNewsDetail(newsId)
            .flatMap { stringNewsDetailInfoBeanMap ->
                //                val list = stringNewsDetailInfoBeanMap[newsId]
//                val i = Gson().toJson(list)
                Observable.just<NewsDetailInfoBean>(stringNewsDetailInfoBeanMap[newsId])//获取NewsDetailInfoBean
            }
            ?.doOnNext { newsDetailInfoBean ->
                handleRichTextWithImg(newsDetailInfoBean)
            }
            ?.ext(mView, scopeProvider!!) {
                mView?.loadData(it)
            }
    }

    /**
     * 处理富文本包含图片的情况
     *
     * @param newsDetailBean 原始数据
     */
    private fun handleRichTextWithImg(newsDetailBean: NewsDetailInfoBean) {
        if (!ListUtils.isEmpty(newsDetailBean.img)) {
            var body = newsDetailBean.body
            for (imgEntity in newsDetailBean.img) {
                val ref = imgEntity.ref
                val src = imgEntity.src
                //                LogUtils.i(TAG, "imgEntity: " + imgEntity.toString());
                val img = HTML_IMG_TEMPLATE.replace("http", src)
                body = body.replace(ref.toRegex(), img)
                //                LogUtils.i(TAG, "img: " + img);
                //                LogUtils.i(TAG, "body: " + body);
            }
            newsDetailBean.body = body
        }
    }
}
