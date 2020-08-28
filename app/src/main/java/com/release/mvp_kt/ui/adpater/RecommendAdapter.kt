package com.release.mvp_kt.ui.adpater

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.release.mvp_kt.R
import com.release.mvp_kt.mvp.model.NewslistBean
import com.release.mvp_kt.utils.DefIconFactory
import com.release.mvp_kt.utils.ImageLoader

/**
 * @author Mr.release
 * @create 2019/3/22
 * @Describe
 */
class RecommendAdapter(layoutResId: Int, data: MutableList<NewslistBean>?) :
    BaseQuickAdapter<NewslistBean, BaseViewHolder>(layoutResId, data) {

    override fun convert(helper: BaseViewHolder, item: NewslistBean) {

        ImageLoader.loadFitCenter(
            context,
            item.picUrl,
            helper.getView<ImageView>(R.id.iv_tuijian),
            DefIconFactory.provideIcon()
        )
        helper.setText(R.id.tv_tuijian_title, item.title)
        helper.setText(R.id.tv_tuijian_time, item.ctime)
    }
}
