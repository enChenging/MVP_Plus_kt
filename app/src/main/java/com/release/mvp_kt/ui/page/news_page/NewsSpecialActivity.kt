package com.release.mvp_kt.ui.page.news_page

import android.animation.Animator
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewStub
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.dl7.tag.TagLayout
import com.dl7.tag.TagView
import com.release.mvp_kt.R
import com.release.mvp_kt.base.BaseMvpActivity
import com.release.mvp_kt.constant.Constant.SPECIAL_KEY
import com.release.mvp_kt.mvp.contract.NewsSpacialContract
import com.release.mvp_kt.mvp.model.bean.SpecialInfoBean
import com.release.mvp_kt.mvp.presenter.NewsSpacialPresenter
import com.release.mvp_kt.rx.SchedulerUtils
import com.release.mvp_kt.ui.adpater.NewsSpecialAdapter
import com.release.mvp_kt.ui.adpater.item.SpecialItem
import com.release.mvp_kt.utils.AnimateHelper
import com.release.mvp_kt.utils.DefIconFactory
import com.release.mvp_kt.utils.ImageLoader
import io.reactivex.Observable
import io.reactivex.functions.Predicate
import kotlinx.android.synthetic.main.activity_special.*


/**
 * 新闻专题
 *
 * @author Mr.release
 * @create 2019/4/15
 * @Describe
 */
@Suppress("DEPRECATION", "PLUGIN_WARNING")
class NewsSpecialActivity : BaseMvpActivity<NewsSpacialContract.View, NewsSpacialContract.Presenter>(),
    NewsSpacialContract.View {


    companion object {
        fun start(context: Context, newsId: String) {
            val intent = Intent(context, NewsSpecialActivity::class.java)
            intent.putExtra(SPECIAL_KEY, newsId)
            context.startActivity(intent)
            (context as Activity).overridePendingTransition(R.anim.slide_right_entry, R.anim.hold)
        }
    }

    override fun createPresenter(): NewsSpacialContract.Presenter = NewsSpacialPresenter()

    override fun initLayoutID(): Int = R.layout.activity_special

    private val mAdapter: NewsSpecialAdapter by lazy {
        NewsSpecialAdapter(R.layout.adapter_news_list, R.layout.adapter_special_head, null)
    }

    private val mSkipId = IntArray(20)
    private var mTagLayout: TagLayout? = null
    private var mLinearLayoutManager: LinearLayoutManager? = null
    private var mTopBarAnimator: Animator? = null
    private var specialId: String? = null

    override fun initData() {
        specialId = intent.getStringExtra(SPECIAL_KEY)
    }

    override fun initView() {
        super.initView()
        tool_bar.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
        mAdapter.run {
            openLoadAnimation(BaseQuickAdapter.SCALEIN)
        }

        rv_news_list.run {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@NewsSpecialActivity)
            adapter = mAdapter
        }
    }

    override fun startNet() {
        mPresenter?.requestData(specialId!!)
    }

    override fun loadHead(data: SpecialInfoBean) {
        val headView = LayoutInflater.from(this).inflate(R.layout.head_special, null)
        val mIvBanner = headView.findViewById<ImageView>(R.id.iv_banner)

        ImageLoader.loadFitCenter(this, data.banner, mIvBanner, DefIconFactory.provideIcon())

        // 添加导语
        if (!TextUtils.isEmpty(data.digest)) {
            val stub = headView.findViewById<ViewStub>(R.id.vs_digest)
            stub.inflate()
            val tvDigest = headView.findViewById<TextView>(R.id.tv_digest)
            tvDigest.text = data.digest
        }
        mTagLayout = headView.findViewById(R.id.tag_layout)
        mAdapter.addHeaderView(headView)
    }

    override fun loadData(data: List<SpecialItem>) {

        mAdapter.setNewData(data)
        handleTagLayout(data)
    }

    internal var change: Boolean = false
    override fun initListener() {

        val topBarHeight = resources.getDimensionPixelSize(R.dimen.default_toolbar_height)

        mTopBarAnimator = AnimateHelper.doMoveVertical(tool_bar, tool_bar!!.translationY.toInt(), -topBarHeight, 0)

        rv_news_list!!.setOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (dy < 0 && !change) {
                    if (AnimateHelper.isRunning(mTopBarAnimator))
                        return
                    mTopBarAnimator = AnimateHelper.doMoveVertical(tool_bar, tool_bar!!.translationY.toInt(), 0, 300)
                    change = true
                } else if (dy > 0 && change) {
                    AnimateHelper.stopAnimator(mTopBarAnimator)
                    ViewCompat.setTranslationY(tool_bar!!, (-topBarHeight).toFloat())
                    change = false
                }
            }
        })


        fab_coping.run {
            setOnClickListener {
                mLinearLayoutManager!!.scrollToPosition(0)
            }
        }
    }

    /**
     * 处理导航标签
     *
     * @param specialItems
     */
    @SuppressLint("CheckResult", "AutoDispose")
    private fun handleTagLayout(specialItems: List<SpecialItem>) {

        Observable.fromIterable(specialItems)
            .compose(SchedulerUtils.ioToMain())
            .filter(object : Predicate<SpecialItem> {
                var i = 0
                var index = mAdapter.getHeaderViewsCount()  // 存在一个 HeadView 所以从1算起

                @Throws(Exception::class)
                override fun test(specialItem: SpecialItem): Boolean {

                    if (specialItem.isHeader) {
                        // 记录头部的列表索引值，用来跳转
                        mSkipId[i++] = index
                    }
                    index++
                    return specialItem.isHeader
                }
            })
            .map { specialItem -> clipHeadStr(specialItem.header) }
            ?.`as`(SchedulerUtils.bindLifecycle(this))
            ?.subscribe { s -> mTagLayout!!.addTag(s) }

        mTagLayout!!.tagClickListener = TagView.OnTagClickListener { position, _, _ ->
            // 跳转到对应positi
            // on,比scrollToPosition（）精确
            mLinearLayoutManager!!.scrollToPositionWithOffset(mSkipId[position], 0)
        }
    }

    private fun clipHeadStr(headStr: String): String? {
        var head: String? = null
        val index = headStr.indexOf(" ")
        if (index != -1) {
            head = headStr.substring(index, headStr.length)
        }
        return head
    }

}
