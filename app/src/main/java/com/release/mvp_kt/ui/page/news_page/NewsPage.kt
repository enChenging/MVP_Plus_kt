package com.release.mvp_kt.ui.page.news_page

import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.orhanobut.logger.Logger
import com.release.mvp_kt.MainActivity
import com.release.mvp_kt.R
import com.release.mvp_kt.base.BaseMvpFragment
import com.release.mvp_kt.base.ViewPagerAdapter
import com.release.mvp_kt.dao.NewsTypeInfo
import com.release.mvp_kt.mvp.contract.NewsPageContract
import com.release.mvp_kt.mvp.presenter.NewsPagePresenter
import kotlinx.android.synthetic.main.layout_top_search.*
import kotlinx.android.synthetic.main.page_news.*
import java.util.*

/**
 * @author Mr.release
 * @create 2019/3/22
 * @Describe
 */
class NewsPage : BaseMvpFragment<NewsPageContract.View, NewsPageContract.Presenter>(), NewsPageContract.View {


    companion object {

        fun newInstance(): NewsPage {
            return NewsPage()
        }
    }

    override fun createPresenter(): NewsPageContract.Presenter = NewsPagePresenter()

    override fun initLayoutID(): Int = R.layout.page_news

    private val mAdapter: ViewPagerAdapter by lazy {

        ViewPagerAdapter(childFragmentManager)
    }

    override fun loadData(NewsTypeIfs: List<NewsTypeInfo>?) {
        Logger.i(NewsTypeIfs.toString())

        val fragments = ArrayList<Fragment>()
        val titles = ArrayList<String>()

        if (NewsTypeIfs != null) {
            for (bean in NewsTypeIfs) {
                titles.add(bean.name)
                fragments.add(NewsListFragment.newInstance(bean.typeId))
            }
        }

        mAdapter.setItems(fragments, titles)
    }

    override fun initView(view: View) {

        view_pager.adapter = mAdapter
        stl_tab_layout.setViewPager(view_pager)
    }


    override fun initListener() {
        iv_setting.run {
            (activity as MainActivity).toggle()
        }

        iv_search.run {
            Toast.makeText(activity, "搜索", Toast.LENGTH_SHORT).show()
        }
    }
}
