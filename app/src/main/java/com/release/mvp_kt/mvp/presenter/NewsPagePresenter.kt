package com.release.mvp_kt.mvp.presenter

import com.release.mvp_kt.base.BasePresenter
import com.release.mvp_kt.dao.NewsTypeInfo
import com.release.mvp_kt.mvp.contract.NewsPageContract
import org.litepal.LitePal

/**
 * @author Mr.release
 * @create 2019/7/11
 * @Describe
 */
class NewsPagePresenter : BasePresenter<NewsPageContract.View>(), NewsPageContract.Presenter {

    override fun requestData() {
        LitePal.findAll(NewsTypeInfo::class.java).let {
            mView?.loadData(it)
        }
    }
}