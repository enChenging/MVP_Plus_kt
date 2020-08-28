package com.release.mvp_kt.ui.adpater.item


import androidx.annotation.IntDef
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.release.mvp_kt.mvp.model.NewsInfoBean

class NewsMultiItem(@param:NewsItemType private val mItemType: Int, val newsBean: NewsInfoBean) : MultiItemEntity {

    companion object {

        const val ITEM_TYPE_NORMAL = 1
        const val ITEM_TYPE_PHOTO_SET = 2
    }

    override val itemType: Int
        get() = mItemType

    override fun toString(): String {
        return "NewsMultiItem(mItemType=$mItemType, newsBean=$newsBean)"
    }

    @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
    @IntDef(ITEM_TYPE_NORMAL, ITEM_TYPE_PHOTO_SET)
    annotation class NewsItemType


}
