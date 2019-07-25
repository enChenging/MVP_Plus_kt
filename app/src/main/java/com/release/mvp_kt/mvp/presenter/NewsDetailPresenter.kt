package com.release.mvp_kt.mvp.presenter

import com.orhanobut.logger.Logger
import com.release.mvp_kt.App
import com.release.mvp_kt.R
import com.release.mvp_kt.base.BasePresenter
import com.release.mvp_kt.http.exception.ExceptionHandle
import com.release.mvp_kt.mvp.contract.NewsDetailContract
import com.release.mvp_kt.mvp.model.NewsDetailModel
import com.release.mvp_kt.mvp.model.bean.NewsDetailInfoBean
import com.release.mvp_kt.utils.ListUtils
import com.release.mvp_kt.utils.NetWorkUtil
import com.uber.autodispose.autoDisposable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * @author Mr.release
 * @create 2019/6/26
 * @Describe
 */
@Suppress("PrivatePropertyName")
class NewsDetailPresenter : BasePresenter<NewsDetailContract.Model, NewsDetailContract.View>(),
    NewsDetailContract.Presenter {

    private val HTML_IMG_TEMPLATE = "<img src=\"http\" />"

    override fun createModel(): NewsDetailContract.Model? = NewsDetailModel()

    override fun requestData(newsId: String) {

        mModel?.requestData(newsId)
            ?.doOnNext {
                newsDetailInfoBean->
                handleRichTextWithImg(newsDetailInfoBean)
            }
            ?.autoDisposable(scopeProvider!!)
            ?.subscribe(object : Observer<NewsDetailInfoBean> {

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

                override fun onNext(t: NewsDetailInfoBean) {
                    Logger.i("NewsDetail--onNext:$t")
                    mView?.loadData(t)
                }

                override fun onError(t: Throwable) {
                    Logger.e("NewsDetail--onError:$t")
                    mView?.hideLoading()
                    mView?.showError(ExceptionHandle.handleException(t))
                }
            })

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
