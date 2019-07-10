package com.release.mvp_kt.mvp.contract

import com.release.mvp_kt.base.IModel
import com.release.mvp_kt.base.IPresenter
import com.release.mvp_kt.base.IView
import com.release.mvp_kt.mvp.model.bean.HttpResult
import com.release.mvp_kt.mvp.model.bean.KnowledgeTreeBody
import io.reactivex.Observable

/**
 * @author Mr.release
 * @create 2019/6/29
 * @Describe
 */
interface KnowledgeTreeContract {

    interface View : IView {

        fun scrollToTop()

        fun setKnowledgeTree(lists: List<KnowledgeTreeBody>)

    }

    interface Presenter : IPresenter<View> {
        fun requestKnowledgeTree()
    }

    interface Model : IModel {
        fun requestKnowledgeTree(): Observable<HttpResult<List<KnowledgeTreeBody>>>
    }
}