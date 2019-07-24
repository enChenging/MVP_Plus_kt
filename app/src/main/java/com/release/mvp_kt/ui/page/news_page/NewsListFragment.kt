package com.release.mvp_kt.ui.page.news_page

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.orhanobut.logger.Logger
import com.release.mvp_kt.R
import com.release.mvp_kt.base.BaseMvpFragment
import com.release.mvp_kt.constant.Constant.NEWS_TYPE_KEY
import com.release.mvp_kt.mvp.contract.NewsListContract
import com.release.mvp_kt.mvp.model.bean.NewsInfoBean
import com.release.mvp_kt.mvp.presenter.NewsListPresenter
import com.release.mvp_kt.ui.adpater.NewsListAdapter
import com.release.mvp_kt.ui.adpater.item.NewsMultiItem
import kotlinx.android.synthetic.main.fragment_news_list.*


/**
 * 要闻
 *
 * @author Mr.release
 * @create 2019/3/22
 * @Describe
 */
class NewsListFragment : BaseMvpFragment<NewsListContract.View, NewsListContract.Presenter>(), NewsListContract.View {

    companion object {

        fun newInstance(newsId: String): NewsListFragment {
            val fragment = NewsListFragment()
            val bundle = Bundle()
            bundle.putString(NEWS_TYPE_KEY, newsId)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun createPresenter(): NewsListContract.Presenter = NewsListPresenter()

    override fun initLayoutID(): Int = R.layout.fragment_news_list

    private var mPage: Int = 0
    private lateinit var newsId: String
    private var isRefresh = true

    private val mAdapter: NewsListAdapter by lazy {
        NewsListAdapter(null)
    }

    override fun initData() {
        newsId = arguments?.getString(NEWS_TYPE_KEY).toString()
    }

    override fun initView(view: View) {
        super.initView(view)
        mAdapter.run {
            openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT)
            setOnLoadMoreListener(
                { mPresenter?.requestData(newsId, mPage) },
                rv_news_list
            )
        }

        rv_news_list.run {
            setHasFixedSize(true)//让RecyclerView避免重新计算大小
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
        }


        refresh_layout.run {
            setOnRefreshListener {
                isRefresh = true
                mAdapter.setEnableLoadMore(false)
                mPresenter?.requestData(newsId, 0)
                finishRefresh(1000)
            }

            setOnLoadMoreListener {
                isRefresh = false
                val page = mAdapter.data.size / 20
                mPresenter?.requestData(newsId, page)
                finishLoadMore(1000)
            }
        }
    }

    override fun startNet() {
        mPresenter?.requestData(newsId, mPage)
    }

    override fun loadAdData(data: NewsInfoBean) {
    }

    override fun loadData(data: List<NewsMultiItem>) {
        mAdapter.run {
            if (isRefresh)
                replaceData(data)
            else
                addData(data)

            val size = data.size
            if (size < data.size) {
                loadMoreEnd(isRefresh)
            } else {
                loadMoreComplete()
            }
        }
    }

}
