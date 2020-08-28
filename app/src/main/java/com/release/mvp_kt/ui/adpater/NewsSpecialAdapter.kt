package com.release.mvp_kt.ui.adpater

import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.chad.library.adapter.base.BaseSectionQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.flyco.labelview.LabelView
import com.release.mvp_kt.R
import com.release.mvp_kt.ui.adpater.item.SpecialItem
import com.release.mvp_kt.ui.page.news_page.NewsDetailActivity
import com.release.mvp_kt.ui.page.news_page.NewsSpecialActivity
import com.release.mvp_kt.utils.DefIconFactory
import com.release.mvp_kt.utils.ImageLoader
import com.release.mvp_kt.utils.NewsUtils
import com.release.mvp_kt.utils.StringUtils

/**
 * @author Mr.release
 * @create 2019/4/15
 * @Describe
 */
class NewsSpecialAdapter(layoutResId: Int, sectionHeadResId: Int, data: MutableList<SpecialItem>?) :
    BaseSectionQuickAdapter<SpecialItem, BaseViewHolder>(sectionHeadResId,layoutResId,data) {

    override fun convertHeader(helper: BaseViewHolder, item: SpecialItem) {
        helper.setText(R.id.tv_head, item.header)
    }

    override fun convert(holder: BaseViewHolder, item: SpecialItem) {
        val newsIcon = holder.getView<ImageView>(R.id.iv_icon)
        ImageLoader.loadCenterCrop(context, item.t.imgsrc, newsIcon, DefIconFactory.provideIcon())
        holder.setText(R.id.tv_title, item.t.title)
            .setText(R.id.tv_source, StringUtils.clipNewsSource(item.t.source))
            .setText(R.id.tv_time, item.t.ptime)

        if (NewsUtils.isNewsSpecial(item.t.skipType)) {
            val labelView = holder.getView<LabelView>(R.id.label_view)
            labelView.visibility = View.VISIBLE
            labelView.bgColor = ContextCompat.getColor(context, R.color.item_special_bg)
            labelView.text = "专题"
        } else if (NewsUtils.isNewsPhotoSet(item.t.skipType)) {
            val labelView = holder.getView<LabelView>(R.id.label_view)
            labelView.visibility = View.VISIBLE
            labelView.bgColor = ContextCompat.getColor(context, R.color.item_photo_set_bg)
            labelView.text = "图集"
        } else {
            holder.setVisible(R.id.label_view, false)
        }

        holder.itemView.setOnClickListener {
            if (NewsUtils.isNewsSpecial(item.t.skipType)) {
                NewsSpecialActivity.start(context, item.t.specialID,item.t.title)
            } else {
                NewsDetailActivity.start(context, item.t.postid,item.t.title)
            }
        }
    }
}
