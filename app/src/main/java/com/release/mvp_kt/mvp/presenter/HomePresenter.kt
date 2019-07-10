package com.release.mvp_kt.mvp.presenter

import com.orhanobut.logger.Logger
import com.release.mvp_kt.ext.ss
import com.release.mvp_kt.mvp.contract.HomeContract
import com.release.mvp_kt.mvp.model.HomeModel
import com.release.mvp_kt.mvp.model.bean.Article
import com.release.mvp_kt.mvp.model.bean.ArticleResponseBody
import com.release.mvp_kt.mvp.model.bean.HttpResult
import com.release.mvp_kt.utils.SettingUtil
import io.reactivex.Observable
import io.reactivex.functions.BiFunction

/**
 * @author Mr.release
 * @create 2019/6/26
 * @Describe
 */
class HomePresenter : CommonPresenter<HomeContract.Model, HomeContract.View>(), HomeContract.Presenter {

    override fun createModel(): HomeContract.Model? = HomeModel()

    override fun requestBanner() {
        mModel?.requestBanner()?.ss(mModel, mView, false) {
            mView?.setBanner(it.data)
        }
    }

    override fun requestArticles(num: Int) {
        mModel?.requestArticles(num)?.ss(mModel, mView, num == 0) {
            mView?.setArticles(it.data)
        }
    }

    override fun requestHomeData() {

        requestBanner()

        val observable = if (!SettingUtil.getIsShowTopArticle()) {
            mModel?.requestArticles(0)
        } else {
            Observable.zip(mModel?.requestTopArticles(), mModel?.requestArticles(0),
                    BiFunction<HttpResult<MutableList<Article>>, HttpResult<ArticleResponseBody>,
                            HttpResult<ArticleResponseBody>> { t1, t2 ->
                        t1.data.forEach {
                            // 置顶数据中没有标识，手动添加一个标识
                            it.top = "1"
                        }
                        t2.data.datas.addAll(0, t1.data)
                        t2
                    })
        }
        observable?.ss(mModel, mView, false) {
            mView?.setArticles(it.data)
        }
    }

}