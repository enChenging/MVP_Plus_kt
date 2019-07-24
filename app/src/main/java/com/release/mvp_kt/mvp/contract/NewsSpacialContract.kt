package com.release.mvp_kt.mvp.contract

import com.release.mvp_kt.base.IModel
import com.release.mvp_kt.base.IPresenter
import com.release.mvp_kt.base.IView
import com.release.mvp_kt.mvp.model.bean.SpecialInfoBean
import com.release.mvp_kt.ui.adpater.item.SpecialItem
import io.reactivex.Flowable
import io.reactivex.Observable


/**
 * @author Mr.release
 * @create 2019/6/26
 * @Describe
 */
interface NewsSpacialContract {

    interface View : IView {

        fun loadHead(data: SpecialInfoBean)

        fun loadData(data: List<SpecialItem>)
    }

    interface Presenter : IPresenter<View> {

        fun requestData(newsId: String)
    }

    interface Model : IModel {

        fun requestData(specialId: String): Observable<SpecialInfoBean>
    }
}