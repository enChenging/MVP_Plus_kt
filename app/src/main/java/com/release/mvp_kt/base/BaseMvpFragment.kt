package com.release.mvp_kt.base

import android.view.View
import com.release.mvp_kt.ext.showToast
import com.release.mvp_kt.widget.EmptyLayout
import kotlinx.android.synthetic.main.layout_empty.*

/**
 * @author Mr.release
 * @create 2019/6/25
 * @Describe
 */
@Suppress("UNCHECKED_CAST")
abstract class BaseMvpFragment<in V : IView, P : IPresenter<V>> : BaseFragment(), IView {

    /**
     * Presenter
     */
    protected var mPresenter: P? = null

    protected abstract fun createPresenter(): P

    override fun initView(view: View) {
        super.initView(view)
        mPresenter = createPresenter()
        mPresenter?.attachView(this as V)
        if (empty_layout != null)
            empty_layout.hide()
    }

    override fun onDestroyView() {
        super.onDestroyView()
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
        if (empty_layout != null) {
            empty_layout.run {
                emptyStatus = EmptyLayout.STATUS_NO_NET
                setRetryListener(object : EmptyLayout.OnRetryListener {
                    override fun onRetry() {
                        doReConnected()
                    }
                })
            }
        }
    }

    override fun showError(errorMsg: String) {
        showToast(errorMsg)
    }


    override fun showMsg(msg: String) {
        showToast(msg)
    }


}