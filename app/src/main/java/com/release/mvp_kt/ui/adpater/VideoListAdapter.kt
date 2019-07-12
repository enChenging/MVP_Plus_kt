package com.release.mvp_kt.ui.adpater

import cn.jzvd.Jzvd
import cn.jzvd.JzvdStd
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.release.mvp_kt.R
import com.release.mvp_kt.dao.VideoInfo
import com.release.mvp_kt.utils.DefIconFactory
import com.release.mvp_kt.utils.ImageLoader

/**
 * @author Mr.release
 * @create 2019/4/16
 * @Describe
 */
class VideoListAdapter(layoutResId: Int, data: List<VideoInfo>?) :
    BaseQuickAdapter<VideoInfo, BaseViewHolder>(layoutResId, data) {

    override fun convert(holder: BaseViewHolder, item: VideoInfo) {

        val videoplayer = holder.getView<JzvdStd>(R.id.videoplayer)

        holder.setText(R.id.tv_title, item.title)

        videoplayer.setUp(item.mp4_url, item.title, Jzvd.SCREEN_NORMAL)

        ImageLoader.loadFitCenter(mContext, item.cover, videoplayer.thumbImageView, DefIconFactory.provideIcon())
    }
}
