package com.release.mvp_kt.base

/**
 * @author Mr.release
 * @create 2019/6/26
 * @Describe
 */
interface IView {

    /**
     * 显示加载
     */
    fun showLoading()

    /**
     * 隐藏加载
     */
    fun hideLoading()

    /**
     * 显示提示信息
     */
    fun showMsg(msg: String)

    /**
     * 显示网络异常，点击重试
     */
    fun showError()

    /**
     * 显示错误信息
     */
    fun showError(errorMsg: String)

}