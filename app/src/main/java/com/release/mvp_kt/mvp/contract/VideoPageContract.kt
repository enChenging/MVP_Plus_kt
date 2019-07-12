package com.release.mvp_kt.mvp.contract

import com.release.mvp_kt.base.IModel
import com.release.mvp_kt.base.IPresenter
import com.release.mvp_kt.base.IView

/**
 * @author Mr.release
 * @create 2019/7/11
 * @Describe
 */
interface VideoPageContract {

    interface View:IView{
        fun loadData()
    }

    interface Presenter:IPresenter<View>{
        fun requestData()
    }

    interface Model:IModel{
        fun requestData()
    }
}