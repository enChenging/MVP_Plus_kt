package com.release.mvp_kt.base

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider

/**
 * @author Mr.release
 * @create 2019/6/25
 * @Describe
 */
abstract class BasePresenter<V : IView> : IPresenter<V>, LifecycleObserver {


    protected var scopeProvider: AndroidLifecycleScopeProvider? = null
    protected var mView: V? = null

    private val isViewAttached: Boolean
        get() = mView != null


    override fun attachView(mView: V) {
        this.mView = mView
        if (mView is LifecycleOwner) {
            scopeProvider = AndroidLifecycleScopeProvider.from((mView as LifecycleOwner))
        }
    }

    override fun detachView() {
        this.mView = null
    }

    open fun checkViewAttached() {
        if (!isViewAttached) throw  RuntimeException("Please call IPresenter.attachView(IBaseView) before" + " requesting data to the IPresenter")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy(owner: LifecycleOwner) {
        owner.lifecycle.removeObserver(this)
    }

}