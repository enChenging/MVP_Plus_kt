package com.release.mvp_kt.mvp.presenter

import com.release.mvp_kt.base.BasePresenter
import com.release.mvp_kt.ext.ss
import com.release.mvp_kt.mvp.contract.WeChatContract
import com.release.mvp_kt.mvp.model.WeChatModel

/**
 * @author Mr.release
 * @create 2019/7/1
 * @Describe
 */
class WeChatPresenter : BasePresenter<WeChatContract.Model, WeChatContract.View>(), WeChatContract.Presenter {

    override fun createModel(): WeChatContract.Model? = WeChatModel()

    override fun getWXChapters() {
        mModel?.getWXChapters()?.ss(mModel, mView) {
            mView?.showWXChapters(it.data)
        }
    }

}