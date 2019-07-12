package com.release.mvp_kt.mvp.contract

import com.release.mvp_kt.base.IModel
import com.release.mvp_kt.base.IPresenter
import com.release.mvp_kt.base.IView


/**
 * @author Mr.release
 * @create 2019/6/26
 * @Describe
 */
interface KindsPageContract {

    interface View : IView

    interface Presenter : IPresenter<View>

    interface Model : IModel
}