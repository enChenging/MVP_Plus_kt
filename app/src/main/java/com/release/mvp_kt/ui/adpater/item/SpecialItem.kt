package com.release.mvp_kt.ui.adpater.item

import com.chad.library.adapter.base.entity.SectionEntity
import com.release.mvp_kt.mvp.model.NewsItemInfoBean

class SpecialItem : SectionEntity<NewsItemInfoBean> {

    constructor(isHeader: Boolean, header: String) : super(isHeader, header)

    constructor(newsItemBean: NewsItemInfoBean) : super(newsItemBean)
}
