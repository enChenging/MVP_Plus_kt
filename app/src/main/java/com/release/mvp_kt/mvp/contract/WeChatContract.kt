package com.release.mvp_kt.mvp.contract

import com.release.mvp_kt.base.IModel
import com.release.mvp_kt.base.IPresenter
import com.release.mvp_kt.base.IView
import com.release.mvp_kt.mvp.model.bean.HttpResult
import com.release.mvp_kt.mvp.model.bean.WXChapterBean
import io.reactivex.Observable

/**
 * @author Mr.release
 * @create 2019/7/1
 * @Describe
 */
interface WeChatContract {

    interface View : IView {

        fun scrollToTop()

        fun showWXChapters(chapters: MutableList<WXChapterBean>)

    }

    interface Presenter : IPresenter<View> {
        fun getWXChapters()
    }

    interface Model : IModel {
        fun getWXChapters(): Observable<HttpResult<MutableList<WXChapterBean>>>
    }

}