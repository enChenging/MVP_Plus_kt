package com.release.mvp_kt.ui.page.news_page

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import cn.bingoogolapple.bgabanner.BGABanner
import com.chad.library.adapter.base.BaseQuickAdapter
import com.release.mvp_kt.R
import com.release.mvp_kt.base.BaseMvpFragment
import com.release.mvp_kt.constant.Constant
import com.release.mvp_kt.constant.Constant.NEWS_TYPE_KEY
import com.release.mvp_kt.constant.Constant.NEWS_TYPE_TITLE
import com.release.mvp_kt.mvp.contract.NewsListContract
import com.release.mvp_kt.mvp.model.NewsInfoBean
import com.release.mvp_kt.mvp.presenter.NewsListPresenter
import com.release.mvp_kt.ui.adpater.NewsListAdapter
import com.release.mvp_kt.ui.adpater.item.NewsMultiItem
import com.release.mvp_kt.utils.ImageLoader
import com.release.mvp_kt.utils.NewsUtils
import kotlinx.android.synthetic.main.fragment_news_list.*


/**
 * 要闻
 *
 * @author Mr.release
 * @create 2019/3/22
 * @Describe
 */
class NewsListFragment : BaseMvpFragment<NewsListContract.View, NewsListContract.Presenter>(),
    NewsListContract.View {

    private lateinit var newsId: String
    private lateinit var newsTitle: String
    private var bannerTitleList: MutableList<String> = ArrayList()
    private var bannerImagedList: MutableList<String> = ArrayList()
    private var mAdData: MutableList<NewsMultiItem> = ArrayList()
    private var banner: BGABanner? = null

    private val mAdapter: NewsListAdapter by lazy {
        NewsListAdapter(null, newsTitle)
    }


    companion object {

        fun newInstance(newsId: String, title: String): NewsListFragment {
            val fragment = NewsListFragment()
            val bundle = Bundle()
            bundle.putString(NEWS_TYPE_KEY, newsId)
            bundle.putString(NEWS_TYPE_TITLE, title)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun createPresenter(): NewsListContract.Presenter = NewsListPresenter()

    override fun initLayoutID(): Int = R.layout.fragment_news_list

    override fun initData() {
        newsId = arguments?.getString(NEWS_TYPE_KEY).toString()
        newsTitle = arguments?.getString(NEWS_TYPE_TITLE).toString()
    }

    override fun initView(view: View) {
        super.initView(view)
        mAdapter.run {
            setAnimationWithDefault(BaseQuickAdapter.AnimationType.SlideInLeft)
        }

        rv_news_list.run {
            setHasFixedSize(true)//让RecyclerView避免重新计算大小
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
        }

        refresh_layout.run {
            setOnRefreshListener {
                mPresenter?.requestData(newsId, 0, isRefresh = true, isShowLoading = false)
                finishRefresh(1000)
            }

            setOnLoadMoreListener {
                val page = mAdapter.data.size / Constant.PAGE
                mPresenter?.requestData(newsId, page, isRefresh = false, isShowLoading = false)
                finishLoadMore(1000)
            }
        }

        if (newsId == Constant.NEWS_TOP_TYPE_ID)
            initBanner()
    }

    private fun initBanner() {
        val bannerView = layoutInflater.inflate(R.layout.item_newslist_banner, null)
        if (!mAdapter.hasHeaderLayout())
            mAdapter.addHeaderView(bannerView)
        banner = bannerView.findViewById<BGABanner>(R.id.banner)
        banner?.run {
            setDelegate(bannerDelegate)
            setAdapter(bannerAdapter)

        }
    }

    private val bannerAdapter =
        BGABanner.Adapter<ImageView, String> { _, itemView, model, _ ->
            context?.let {
                model?.let { it1 ->
                    ImageLoader.loadCenterCrop(
                        it,
                        it1,
                        itemView,
                        R.mipmap.placeholder_banner
                    )
                }
            }
        }

    /**
     * BannerClickListener
     */
    private val bannerDelegate =
        BGABanner.Delegate<ImageView, String> { _, _, _, position ->
            val item = mAdData[position]
            val itemNewsBean = item.newsBean
            when (item.itemType) {
                NewsMultiItem.ITEM_TYPE_NORMAL -> if (NewsUtils.isNewsSpecial(itemNewsBean.skipType))
                    activity?.let {
                        NewsSpecialActivity.start(
                            it,
                            itemNewsBean.specialID,
                            itemNewsBean.title
                        )
                    }
                else
                    activity?.let {
                        NewsDetailActivity.start(
                            it,
                            itemNewsBean.postid,
                            itemNewsBean.title
                        )
                    }
                NewsMultiItem.ITEM_TYPE_PHOTO_SET -> activity?.let {
                    PhotoAlbumActivity.start(
                        it,
                        itemNewsBean.photosetID
                    )
                }
            }
        }

    override fun startNet() {
        mPresenter?.requestData(newsId, 0, isRefresh = false, isShowLoading = true)
    }

    override fun loadAdData(data: NewsInfoBean) {

    }

    override fun loadData(data: List<NewsMultiItem>, isRefresh: Boolean, isShowLoading: Boolean) {
        mAdapter.run {
            if (isRefresh || isShowLoading) {
                initBannerData(data)
                setList(data)
            } else
                addData(data)
        }
    }

    private fun initBannerData(data: List<NewsMultiItem>) {
        if (newsId == Constant.NEWS_TOP_TYPE_ID) {
            mAdData.clear()
            bannerTitleList.clear()
            bannerImagedList.clear()
            for (i in 10..14) {
                val newsBean = data[i].newsBean
                bannerTitleList.add(newsBean.title)
                mAdData.add(data[i])
                bannerImagedList.add(newsBean.imgsrc)
            }
            banner?.setData(bannerImagedList, bannerTitleList)
        }
    }

}
