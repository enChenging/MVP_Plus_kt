package com.release.mvp_kt.base

import io.reactivex.disposables.Disposable

/**
 * @author Mr.release
 * @create 2019/6/26
 * @Describe
 */
interface IModel {

    fun addDisposable(disposable: Disposable?)

    fun onDetach()

}