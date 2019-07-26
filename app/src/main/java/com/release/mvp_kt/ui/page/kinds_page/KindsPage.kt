package com.release.mvp_kt.ui.page.kinds_page


import com.release.mvp_kt.R
import com.release.mvp_kt.base.BaseMvpFragment
import com.release.mvp_kt.mvp.contract.KindsPageContract
import com.release.mvp_kt.mvp.presenter.KindsPresenter

/**
 * @author Mr.release
 * @create 2019/3/22
 * @Describe
 */
class KindsPage : BaseMvpFragment<KindsPageContract.View, KindsPageContract.Presenter>(), KindsPageContract.View {

    override fun createPresenter(): KindsPageContract.Presenter = KindsPresenter()

    override fun initLayoutID(): Int = R.layout.page_kinds


}
