package com.release.mvp_kt.mvp.presenter

import com.release.mvp_kt.ext.ss
import com.release.mvp_kt.mvp.contract.ProjectListContract
import com.release.mvp_kt.mvp.model.ProjectListModel

/**
 * @author Mr.release
 * @create 2019/7/1
 * @Describe
 */
class ProjectListPresenter : CommonPresenter<ProjectListContract.Model, ProjectListContract.View>(), ProjectListContract.Presenter {

    override fun createModel(): ProjectListContract.Model? = ProjectListModel()

    override fun requestProjectList(page: Int, cid: Int) {
        mModel?.requestProjectList(page, cid)?.ss(mModel, mView, page == 1) {
            mView?.setProjectList(it.data)
        }
    }

}