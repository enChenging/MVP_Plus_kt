package com.release.mvp_kt.mvp.contract

import com.release.mvp_kt.base.IPresenter
import com.release.mvp_kt.base.IView

/**
 * @author Mr.release
 * @create 2019/6/25
 * @Describe
 */
interface WebContracnt {

    interface View : IView

    interface Presenter : IPresenter<View>
}