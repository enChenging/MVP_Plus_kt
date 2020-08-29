package com.release.mvp_kt.ui.page.recommend_page

import android.app.ActivityOptions
import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.release.mvp_kt.R
import com.release.mvp_kt.base.BaseMvpFragment
import com.release.mvp_kt.constant.Constant
import com.release.mvp_kt.http.api.BaseURL
import com.release.mvp_kt.mvp.contract.RecommendPageContract
import com.release.mvp_kt.mvp.model.NewslistBean
import com.release.mvp_kt.mvp.presenter.RecommendPagePresenter
import com.release.mvp_kt.ui.activity.WebActivity
import com.release.mvp_kt.ui.adpater.RecommendAdapter
import kotlinx.android.synthetic.main.page_recommend.*

/**
 * @author Mr.release
 * @create 2019/3/22
 * @Describe
 */
class RecommendPage :
    BaseMvpFragment<RecommendPageContract.View, RecommendPageContract.Presenter>(),
    RecommendPageContract.View {

    override fun createPresenter(): RecommendPageContract.Presenter = RecommendPagePresenter()

    override fun initLayoutID(): Int = R.layout.page_recommend

    private val mAdapter: RecommendAdapter by lazy {
        RecommendAdapter(R.layout.item_recommend, null)
    }

    override fun initView(view: View) {
        super.initView(view)


        refresh_layout.run {
            refresh_layout.setEnableLoadMore(false)
            setOnRefreshListener {
                finishRefresh(1000)
                mPresenter?.requestData(BaseURL.RECOMMEND_ID, Constant.PAGE, true,
                    isShowLoading = false
                )
            }
        }

        rv_list.run {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = mAdapter
        }

        mAdapter.run {
            setOnItemClickListener { adapter, _, position ->
                val bean = adapter.data[position] as NewslistBean

                val options: ActivityOptions = ActivityOptions.makeScaleUpAnimation(
                    view,
                    view.width / 2,
                    view.height / 2,
                    0,
                    0
                )

                Intent(context, WebActivity::class.java).run {
                    putExtra(Constant.CONTENT_URL_KEY, bean.url)
                    putExtra(Constant.CONTENT_TITLE_KEY, bean.title)
                    context?.startActivity(this, options.toBundle())
                }
            }
        }
    }

    override fun startNet() {
        mPresenter?.requestData(BaseURL.RECOMMEND_ID, Constant.PAGE, false, isShowLoading = true)
    }

    override fun loadData(data: MutableList<NewslistBean>,isRefresh :Boolean) {

        mAdapter.setList(data)
    }

}
