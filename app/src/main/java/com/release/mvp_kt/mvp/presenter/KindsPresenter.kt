package com.release.mvp_kt.mvp.presenter

import com.release.mvp_kt.base.BasePresenter
import com.release.mvp_kt.mvp.contract.KindsPageContract

/**
 * @author Mr.release
 * @create 2019/6/26
 * @Describe
 */
class KindsPresenter : BasePresenter< KindsPageContract.View>(), KindsPageContract.Presenter
