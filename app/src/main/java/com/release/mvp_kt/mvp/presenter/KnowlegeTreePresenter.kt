package com.release.mvp_kt.mvp.presenter

import com.release.mvp_kt.base.BasePresenter
import com.release.mvp_kt.ext.ss
import com.release.mvp_kt.mvp.contract.KnowledgeTreeContract
import com.release.mvp_kt.mvp.model.KnowlegeTreeModel

/**
 * @author Mr.release
 * @create 2019/6/29
 * @Describe
 */
class KnowlegeTreePresenter : BasePresenter<KnowledgeTreeContract.Model,KnowledgeTreeContract.View>(),KnowledgeTreeContract.Presenter{

    override fun createModel(): KnowledgeTreeContract.Model? = KnowlegeTreeModel()
    override fun requestKnowledgeTree() {
        mModel?.requestKnowledgeTree()?.ss(mModel,mView){
            mView?.setKnowledgeTree(it.data)
        }
    }

}