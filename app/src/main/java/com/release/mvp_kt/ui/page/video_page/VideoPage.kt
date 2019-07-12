package com.release.mvp_kt.ui.page.video_page

import android.view.View
import androidx.fragment.app.Fragment
import com.release.mvp_kt.R
import com.release.mvp_kt.base.BaseMvpFragment
import com.release.mvp_kt.base.ViewPagerAdapter
import com.release.mvp_kt.mvp.contract.VideoPageContract
import com.release.mvp_kt.mvp.presenter.VideoPagePresenter
import kotlinx.android.synthetic.main.page_video.*
import java.util.*

/**
 * @author Mr.release
 * @create 2019/3/22
 * @Describe
 */
class VideoPage : BaseMvpFragment<VideoPageContract.View, VideoPageContract.Presenter>(), VideoPageContract.View {

    companion object {

        fun newInstance(): VideoPage {
            return VideoPage()
        }
    }

    override fun createPresenter(): VideoPageContract.Presenter = VideoPagePresenter()

    override fun initLayoutID(): Int = R.layout.page_video


    private val VIDEO_ID = arrayOf("V9LG4B3A0", "V9LG4E6VR", "V9LG4CHOR", "00850FRB")
    private val VIDEO_TITLE = arrayOf("热点", "搞笑", "娱乐", "精品")

    private val mAdapter: ViewPagerAdapter by lazy {
        ViewPagerAdapter(childFragmentManager)
    }

    private var fragments: MutableList<Fragment> = ArrayList()

    override fun initData() {


        for (i in VIDEO_ID.indices) {
            fragments.add(VideoListFragment.newInstance(VIDEO_ID[i]))
        }
        mAdapter.setItems(fragments, VIDEO_TITLE)
    }

    override fun initView(view: View) {


        view_pager.adapter = mAdapter
        stl_tab_layout.setViewPager(view_pager)
    }


    override fun loadData() {


    }
}
