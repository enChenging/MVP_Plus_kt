package com.release.mvp_kt.mvp.model

import com.release.mvp_kt.base.BaseModel
import com.release.mvp_kt.http.RetrofitHelper
import com.release.mvp_kt.mvp.contract.WeChatContract
import com.release.mvp_kt.mvp.model.bean.HttpResult
import com.release.mvp_kt.mvp.model.bean.WXChapterBean
import io.reactivex.Observable

/**
 * @author Mr.release
 * @create 2019/7/1
 * @Describe
 */
class WeChatModel : BaseModel(), WeChatContract.Model {

    override fun getWXChapters(): Observable<HttpResult<MutableList<WXChapterBean>>> {
        return RetrofitHelper.service.getWXChapters()
    }

}