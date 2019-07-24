package com.release.mvp_kt.mvp.model

import com.release.mvp_kt.base.BaseModel
import com.release.mvp_kt.dao.NewsTypeInfo
import com.release.mvp_kt.mvp.contract.NewsPageContract
import org.litepal.LitePal

/**
 * @author Mr.release
 * @create 2019/7/11
 * @Describe
 */
class NewsPageModel : BaseModel(), NewsPageContract.Model {

    override fun requestData(): MutableList<NewsTypeInfo> {
       return LitePal.findAll(NewsTypeInfo::class.java)
    }
}