package com.release.mvp_kt.mvp.presenter

import com.release.mvp_kt.base.BasePresenter
import com.release.mvp_kt.mvp.contract.MainContract
import com.release.mvp_kt.mvp.model.MainModel

/**
 * @author Mr.release
 * @create 2019/6/26
 * @Describe
 */
class MainPresenter : BasePresenter<MainContract.Model, MainContract.View>(), MainContract.Presenter {

    override fun createModel(): MainContract.Model? = MainModel()


}
