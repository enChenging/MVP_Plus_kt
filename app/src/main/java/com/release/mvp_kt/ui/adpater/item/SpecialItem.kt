package com.release.mvp_kt.ui.adpater.item

import com.chad.library.adapter.base.entity.SectionEntity
import com.release.mvp_kt.mvp.model.NewsItemInfoBean

class SpecialItem(override val isHeader: Boolean) : SectionEntity {

    var header: String = ""
    lateinit var t: NewsItemInfoBean

    constructor(
        isHeader: Boolean,
        header: String
    ) : this(isHeader){
        this.header = header
    }

    constructor(
        newsItemBean: NewsItemInfoBean
    ) : this(false){
        this.t = newsItemBean
    }

}



