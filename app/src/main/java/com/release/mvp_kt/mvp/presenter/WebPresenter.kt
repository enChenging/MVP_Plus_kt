package com.release.mvp_kt.mvp.presenter

import com.release.mvp_kt.base.BasePresenter
import com.release.mvp_kt.mvp.contract.WebContracnt
import com.release.mvp_kt.mvp.model.WebModel

/**
 * @author Mr.release
 * @create 2019/6/25
 * @Describe
 */
class WebPresenter :BasePresenter<WebContracnt.Model,WebContracnt.View>(),WebContracnt.Presenter {

    override fun createModel(): WebContracnt.Model?  = WebModel()
}