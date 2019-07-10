package com.release.mvp_kt.mvp.contract

/**
 * @author Mr.release
 * @create 2019/6/25
 * @Describe
 */
interface WebContracnt {

    interface View :CommonContract.View

    interface Presenter:CommonContract.Presenter<View>

    interface Model:CommonContract.Model
}