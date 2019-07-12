package com.release.mvp_kt.ui.page.recommend_page

import android.app.ActivityOptions
import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.release.mvp_kt.R
import com.release.mvp_kt.base.BaseMvpFragment
import com.release.mvp_kt.constant.Constant
import com.release.mvp_kt.mvp.contract.RecommendPageContract
import com.release.mvp_kt.mvp.model.bean.NewslistBean
import com.release.mvp_kt.mvp.presenter.RecommendPagePresenter
import com.release.mvp_kt.ui.activity.WebActivity
import com.release.mvp_kt.ui.adpater.RecommendAdapter
import kotlinx.android.synthetic.main.page_recommend.*

/**
 * @author Mr.release
 * @create 2019/3/22
 * @Describe
 */
class RecommendPage : BaseMvpFragment<RecommendPageContract.View, RecommendPageContract.Presenter>(),
    RecommendPageContract.View {

    companion object {
        fun newInstance(): RecommendPage {
            return RecommendPage()
        }
    }

    override fun createPresenter(): RecommendPageContract.Presenter = RecommendPagePresenter()

    override fun initLayoutID(): Int = R.layout.page_recommend

    private val mAdapter: RecommendAdapter by lazy {
        RecommendAdapter(R.layout.item_recommend, null)
    }

    override fun initView(view: View) {
        rv_list.run {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = mAdapter
        }

        mAdapter.run {
            onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, _, position ->
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
//                        WebDetailActivity.start(activity, bean.title, bean.ctime, bean.description, bean.url)
            }
        }

        mPresenter?.requestData()
    }

    override fun loadData(data: List<NewslistBean>) {

        mAdapter.setNewData(data)
    }

}
