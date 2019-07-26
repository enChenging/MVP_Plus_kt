package com.release.mvp_kt.mvp.contract

import com.release.mvp_kt.base.IPresenter
import com.release.mvp_kt.base.IView


/**
 * @author Mr.release
 * @create 2019/6/26
 * @Describe
 */
interface MainContract {

    interface View : IView

    interface Presenter: IPresenter<View>
}