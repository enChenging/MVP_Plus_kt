package com.release.mvp_kt.ui.page.video_page

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.jzvd.Jzvd
import com.chad.library.adapter.base.BaseQuickAdapter
import com.release.mvp_kt.R
import com.release.mvp_kt.base.BaseMvpFragment
import com.release.mvp_kt.constant.Constant.VIDEO_ID_KEY
import com.release.mvp_kt.dao.VideoInfo
import com.release.mvp_kt.mvp.contract.VideoListContract
import com.release.mvp_kt.mvp.model.bean.NewsDetailInfoBean
import com.release.mvp_kt.mvp.presenter.VideoListPresenter
import com.release.mvp_kt.ui.adpater.VideoListAdapter
import kotlinx.android.synthetic.main.fragment_video_list.*

/**
 * @author Mr.release
 * @create 2019/4/16
 * @Describe
 */
class VideoListFragment : BaseMvpFragment<VideoListContract.View, VideoListContract.Presenter>(),
    VideoListContract.View {

    companion object {

        fun newInstance(videoId: String): VideoListFragment {
            val fragment = VideoListFragment()
            val bundle = Bundle()
            bundle.putString(VIDEO_ID_KEY, videoId)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun createPresenter(): VideoListContract.Presenter = VideoListPresenter()

    override fun initLayoutID(): Int = R.layout.fragment_video_list

    private var isRefresh = true

    private lateinit var mVideoId: String

    private val mAdapter: VideoListAdapter by lazy {
        VideoListAdapter(R.layout.adapter_video_list, null)
    }

    override fun initData() {
        mVideoId = arguments?.getString(VIDEO_ID_KEY).toString()

        mPresenter?.requestData(mVideoId, 0)
    }

    override fun initView(view: View) {

        refresh_layout.run {
            setOnRefreshListener {
                isRefresh = true
                mAdapter.setEnableLoadMore(false)
                mPresenter?.requestData(mVideoId,0)
                finishRefresh(1000)
            }

            setOnLoadMoreListener {
                isRefresh = false
                val page = mAdapter.data.size / 20
                mPresenter?.requestData(mVideoId,page)
                finishLoadMore(1000)
            }
        }

        rv_photo_list.run {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
            addOnChildAttachStateChangeListener(object : RecyclerView.OnChildAttachStateChangeListener {
                override fun onChildViewAttachedToWindow(view: View) {

                }

                override fun onChildViewDetachedFromWindow(view: View) {
                    val jzvd = view.findViewById<Jzvd>(R.id.videoplayer)
                    if (jzvd != null && Jzvd.CURRENT_JZVD != null &&
                        jzvd.jzDataSource.containsTheUrl(Jzvd.CURRENT_JZVD.jzDataSource.currentUrl)
                    ) {
                        if (Jzvd.CURRENT_JZVD != null && Jzvd.CURRENT_JZVD.currentScreen != Jzvd.SCREEN_WINDOW_FULLSCREEN) {
                            Jzvd.resetAllVideos()
                        }
                    }
                }
            })
        }
    }

    override fun loadData(data: List<VideoInfo>) {
        mAdapter.run {
            if (isRefresh) {
                replaceData(data)
            } else {
                addData(data)
            }
            val size = data.size
            if (size < data.size) {
                loadMoreEnd(isRefresh)
            } else {
                loadMoreComplete()
            }
        }
    }

}