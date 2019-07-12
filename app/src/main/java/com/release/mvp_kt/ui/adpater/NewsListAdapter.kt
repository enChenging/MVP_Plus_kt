package com.release.mvp_kt.ui.adpater

import android.view.View
import android.widget.ImageView
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.flyco.labelview.LabelView
import com.release.mvp_kt.R
import com.release.mvp_kt.mvp.model.bean.NewsInfoBean
import com.release.mvp_kt.ui.adpater.item.NewsMultiItem
import com.release.mvp_kt.ui.page.news_page.NewsDetailActivity
import com.release.mvp_kt.ui.page.news_page.NewsSpecialActivity
import com.release.mvp_kt.ui.page.news_page.PhotoAlbumActivity
import com.release.mvp_kt.utils.*
import com.release.mvp_kt.widget.RippleView
import kotlin.math.min

/**
 * 要闻
 *
 * @author Mr.release
 * @create 2019/3/22
 * @Describe
 */
class NewsListAdapter(data: List<NewsMultiItem>?) : BaseMultiItemQuickAdapter<NewsMultiItem, BaseViewHolder>(data) {


    init {
        addItemType(NewsMultiItem.ITEM_TYPE_NORMAL, R.layout.adapter_news_list)
        addItemType(NewsMultiItem.ITEM_TYPE_PHOTO_SET, R.layout.adapter_news_photo_set)
    }

    override fun convert(helper: BaseViewHolder, item: NewsMultiItem) {
        when (item.itemType) {
            NewsMultiItem.ITEM_TYPE_NORMAL -> _handleNewsNormal(helper, item.newsBean)
            NewsMultiItem.ITEM_TYPE_PHOTO_SET -> _handleNewsPhotoSet(helper, item.newsBean)
        }
    }

    /**
     * 处理正常的新闻
     *
     * @param holder
     * @param item
     */
    private fun _handleNewsNormal(holder: BaseViewHolder, item: NewsInfoBean) {
        val newsIcon = holder.getView<ImageView>(R.id.iv_icon)
        ImageLoader.loadCenterCrop(mContext, item.imgsrc, newsIcon, DefIconFactory.provideIcon())
        holder.setText(R.id.tv_title, item.title)
            .setText(R.id.tv_source, StringUtils.clipNewsSource(item.source))
            .setText(R.id.tv_time, item.ptime)
        // 设置标签
        if (NewsUtils.isNewsSpecial(item.skipType)) {
            val labelView = holder.getView<LabelView>(R.id.label_view)
            labelView.visibility = View.VISIBLE
            labelView.text = "专题"
        } else {
            holder.setVisible(R.id.label_view, false)
        }
        // 波纹效果
        val rippleLayout = holder.getView<RippleView>(R.id.item_ripple)
        rippleLayout.setOnRippleCompleteListener {
            if (NewsUtils.isNewsSpecial(item.skipType))
                NewsSpecialActivity.start(mContext, item.specialID)
            else
                NewsDetailActivity.start(mContext, item.postid)
        }
    }

    /**
     * 处理图片的新闻
     *
     * @param holder
     * @param item
     */
    private fun _handleNewsPhotoSet(holder: BaseViewHolder, item: NewsInfoBean) {
        val newsPhoto = arrayOfNulls<ImageView>(3)
        newsPhoto[0] = holder.getView<ImageView>(R.id.iv_icon_1)
        newsPhoto[1] = holder.getView<ImageView>(R.id.iv_icon_2)
        newsPhoto[2] = holder.getView<ImageView>(R.id.iv_icon_3)
        holder.setVisible(R.id.iv_icon_2, false).setVisible(R.id.iv_icon_3, false)
        newsPhoto[0]?.let { ImageLoader.loadCenterCrop(mContext, item.imgsrc, it, DefIconFactory.provideIcon()) }
        if (!ListUtils.isEmpty(item.imgextra)) {
            for (i in 0 until min(2, item.imgextra.size)) {
                newsPhoto[i + 1]?.visibility = View.VISIBLE
                newsPhoto[i + 1]?.let {
                    ImageLoader.loadCenterCrop(
                        mContext, item.imgextra[i].imgsrc,
                        it, DefIconFactory.provideIcon()
                    )
                }
            }
        }
        holder.setText(R.id.tv_title, item.title)
            .setText(R.id.tv_source, StringUtils.clipNewsSource(item.source))
            .setText(R.id.tv_time, item.ptime)
        // 波纹效果
        val rippleLayout = holder.getView<RippleView>(R.id.item_ripple)
        rippleLayout.setOnRippleCompleteListener { PhotoAlbumActivity.start(mContext, item.photosetID) }
    }
}