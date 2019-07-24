package com.release.mvp_kt.base

import com.release.mvp_kt.ext.showToast
import com.release.mvp_kt.widget.EmptyLayout
import kotlinx.android.synthetic.main.layout_empty.*

/**
 * @author Mr.release
 * @create 2019/6/25
 * @Describe
 */
@Suppress("UNCHECKED_CAST")
abstract class BaseMvpActivity<in V : IView, P : IPresenter<V>> : BaseActivity(), IView {

    protected var mPresenter: P? = null

    protected abstract fun createPresenter(): P

    override fun initView() {
        mPresenter = createPresenter()
        mPresenter?.attachView(this as V)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.detachView()
        this.mPresenter = null
    }

    override fun showLoading() {
        if (empty_layout != null)
            empty_layout.emptyStatus = EmptyLayout.STATUS_LOADING
    }

    override fun hideLoading() {
        if (empty_layout != null)
            empty_layout.hide()
    }

    override fun showError() {
        if (empty_layout != null)
            empty_layout.emptyStatus = EmptyLayout.STATUS_NO_NET
    }

    override fun showError(errorMsg: String) {
        showToast(errorMsg)
    }

    override fun showDefaultMsg(msg: String) {
        showToast(msg)
    }

    override fun showMsg(msg: String) {
        showToast(msg)
    }
}