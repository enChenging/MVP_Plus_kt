package com.release.mvp_kt.ui.page.news_page

import android.animation.Animator
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.view.ViewConfiguration
import android.widget.ScrollView
import androidx.core.view.ViewCompat
import androidx.core.widget.NestedScrollView
import com.release.mvp_kt.R
import com.release.mvp_kt.base.BaseMvpActivity
import com.release.mvp_kt.constant.Constant.NEWS_ID_KEY
import com.release.mvp_kt.mvp.contract.NewsDetailContract
import com.release.mvp_kt.mvp.model.bean.NewsDetailInfoBean
import com.release.mvp_kt.mvp.model.bean.SpinfoBean
import com.release.mvp_kt.mvp.presenter.NewsDetailPresenter
import com.release.mvp_kt.utils.AnimateHelper
import com.release.mvp_kt.utils.ListUtils
import com.release.mvp_kt.utils.NewsUtils
import com.release.mvp_kt.widget.PullScrollView
import com.zzhoujay.richtext.RichText
import kotlinx.android.synthetic.main.fragment_news_detail.*
import kotlinx.android.synthetic.main.layout_news_content.*
import kotlinx.android.synthetic.main.layout_pull_scrollview_foot.*
import kotlinx.android.synthetic.main.layout_related_content.*
import kotlin.math.abs

/**
 * 新闻详情
 * @author Mr.release
 * @create 2019/4/15
 * @Describe
 */
@Suppress("DEPRECATION")
class NewsDetailActivity : BaseMvpActivity<NewsDetailContract.View, NewsDetailContract.Presenter>(),
    NewsDetailContract.View {

    companion object {
        fun start(context: Context, newsId: String) {
            val intent = Intent(context, NewsDetailActivity::class.java)
            intent.putExtra(NEWS_ID_KEY, newsId)
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            context.startActivity(intent)
            (context as Activity).overridePendingTransition(R.anim.slide_right_entry, R.anim.hold)
        }
    }

    override fun createPresenter(): NewsDetailContract.Presenter = NewsDetailPresenter()

    override fun initLayoutID(): Int = R.layout.fragment_news_detail


    private var mMinScrollSlop: Int = 0
    private var mTopBarAnimator: Animator? = null
    private var mLastScrollY = 0
    private var mNextNewsId: String? = null
    private var newsId: String? = null

    override fun initData() {
        newsId = intent.getStringExtra(NEWS_ID_KEY)
    }

    override fun initView() {
        super.initView()
        RichText.initCacheDir(this)
        RichText.debugMode = false
        // 最小触摸滑动距离
        mMinScrollSlop = ViewConfiguration.get(this).scaledTouchSlop
    }

    override fun startNet() {
        mPresenter?.requestData(newsId!!)
    }

    override fun loadData(data: NewsDetailInfoBean) {
        tv_title_content.text = data.title
        tv_source.text = data.source
        tv_time.text = data.ptime

        RichText.from(data.body).into(tv_content)
        handleSpInfo(data.spinfo)
        handleRelatedNews(data)
    }

    fun startInside(newsId: String?) {
        val intent = Intent(this, NewsDetailActivity::class.java)
        intent.putExtra(NEWS_ID_KEY, newsId)
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_bottom_entry, R.anim.hold)
    }


    override fun initListener() {
        val topBarHeight = getResources().getDimensionPixelSize(R.dimen.default_toolbar_height)

        back2.setOnClickListener { finish() }

        scroll_view.run {
            setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { _, _, _, _, _ ->
                if (scrollY > topBarHeight) {
                    if (AnimateHelper.isRunning(mTopBarAnimator)) {
                        return@OnScrollChangeListener
                    }
                    if (abs(scrollY - mLastScrollY) > mMinScrollSlop) {
                        val isPullUp = scrollY > mLastScrollY
                        mLastScrollY = scrollY
                        if (isPullUp && ll_top_bar2!!.translationY != (-topBarHeight).toFloat()) {
                            mTopBarAnimator = AnimateHelper.doMoveVertical(
                                ll_top_bar2, ll_top_bar2!!.translationY.toInt(),
                                -topBarHeight, 300
                            )
                        } else if (!isPullUp && ll_top_bar2!!.translationY != 0f) {
                            mTopBarAnimator = AnimateHelper.doMoveVertical(
                                ll_top_bar2, ll_top_bar2!!.translationY.toInt(),
                                0, 300
                            )
                        }
                    }
                } else {
                    if (ll_top_bar2!!.translationY != (-topBarHeight).toFloat()) {
                        AnimateHelper.stopAnimator(mTopBarAnimator)
                        ViewCompat.setTranslationY(ll_top_bar2!!, (-topBarHeight).toFloat())
                        mLastScrollY = 0
                    }
                }
            })

            setFootView(ll_foot_view)
            setPullListener(object : PullScrollView.OnPullListener {
                override fun isDoPull(): Boolean {
                    return true
                }

                override fun handlePull(): Boolean {
                    return if (TextUtils.isEmpty(mNextNewsId)) {
                        false
                    } else {
                        startInside(mNextNewsId)
                        true
                    }
                }

            })
        }

        fab_coping.run {
            setOnClickListener {
                scroll_view.fullScroll(ScrollView.FOCUS_UP)
            }
        }
    }


    /**
     * 处理关联的内容
     *
     * @param spinfo
     */
    private fun handleSpInfo(spinfo: List<SpinfoBean>) {
        if (!ListUtils.isEmpty(spinfo)) {
            vs_related_content!!.inflate()
            tv_type.text = (spinfo[0].sptype)
            RichText.from(spinfo[0].spcontent)
                // 这里处理超链接的点击
                .urlClick { url ->
                    val newsId = NewsUtils.clipNewsIdFromUrl(url)
                    if (newsId != null) {
                        mPresenter?.requestData(newsId)
                    }
                    true
                }
                .into(tv_related_content)
        }
    }

    /**
     * 处理关联新闻
     *
     * @param newsDetailBean
     */
    private fun handleRelatedNews(newsDetailBean: NewsDetailInfoBean) {
        if (ListUtils.isEmpty(newsDetailBean.relative_sys)) {
            tv_next_title.text = "没有相关文章了"
        } else {
            mNextNewsId = newsDetailBean.relative_sys.get(0).id
            tv_next_title.text = newsDetailBean.relative_sys[0].title
        }
    }

    override fun onStop() {
        super.onStop()
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        RichText.recycle()
    }


}
