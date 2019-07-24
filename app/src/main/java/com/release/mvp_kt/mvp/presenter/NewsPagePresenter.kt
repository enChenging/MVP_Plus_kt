package com.release.mvp_kt.mvp.presenter

import com.release.mvp_kt.base.BasePresenter
import com.release.mvp_kt.mvp.contract.NewsPageContract
import com.release.mvp_kt.mvp.model.NewsPageModel

/**
 * @author Mr.release
 * @create 2019/7/11
 * @Describe
 */
class NewsPagePresenter : BasePresenter<NewsPageContract.Model, NewsPageContract.View>(), NewsPageContract.Presenter {


    override fun createModel(): NewsPageContract.Model = NewsPageModel()

    override fun requestData() {
        mModel?.requestData().let {
            mView?.loadData(it)
        }
    }
}