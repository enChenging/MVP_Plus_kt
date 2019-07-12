package com.release.mvp_kt

import android.view.KeyEvent
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import cn.jzvd.Jzvd
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import com.release.mvp_kt.base.BaseMvpActivity
import com.release.mvp_kt.ext.showToast
import com.release.mvp_kt.mvp.contract.MainContract
import com.release.mvp_kt.mvp.presenter.MainPresenter
import com.release.mvp_kt.ui.adpater.ViewPagerAdapter
import com.release.mvp_kt.ui.page.kinds_page.KindsPage
import com.release.mvp_kt.ui.page.news_page.NewsPage
import com.release.mvp_kt.ui.page.recommend_page.RecommendPage
import com.release.mvp_kt.ui.page.video_page.VideoPage
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * @author Mr.release
 * @create 2019/7/10
 * @Describe
 */
open class MainActivity : BaseMvpActivity<MainContract.View, MainContract.Presenter>(), MainContract.View {


    override fun createPresenter(): MainContract.Presenter = MainPresenter()

    override fun initLayoutID(): Int = R.layout.activity_main

    private val fragments = ArrayList<Fragment>(4)

    private val mAdapter: ViewPagerAdapter by lazy {
        ViewPagerAdapter(fragments, supportFragmentManager)
    }

    private var mTitles: Array<String> =
        arrayOf(
            "新闻",
            "视频",
            "咨询",
            "更多"
        )

    override fun initData() {
        fragments.clear()
        fragments.add(NewsPage.newInstance())
        fragments.add(VideoPage.newInstance())
        fragments.add(RecommendPage.newInstance())
        fragments.add(KindsPage.newInstance())

        vp_main.run {
            adapter = mAdapter
            offscreenPageLimit = 5
        }
    }

    override fun initView() {

        left_navigation.itemIconTintList = null

        val headImg = left_navigation.getHeaderView(0).findViewById<ImageView>(R.id.headImg)
        Glide.with(this).load("https://b-ssl.duitang.com/uploads/item/201802/20/20180220170028_JcYMU.jpeg").circleCrop()
            .into(headImg)

        bottom_navigation.apply {
            labelVisibilityMode = LabelVisibilityMode.LABEL_VISIBILITY_LABELED
            setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        }
    }

    override fun initListener() {

        dl_drawer.run {
            setScrimColor(ContextCompat.getColor(this@MainActivity, R.color.black_alpha_32))
            addDrawerListener(object : DrawerLayout.DrawerListener {
                override fun onDrawerStateChanged(newState: Int) {

                }

                override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                    dl_drawer.getChildAt(0).translationX = drawerView.width * slideOffset
                }

                override fun onDrawerClosed(drawerView: View) {
                }

                override fun onDrawerOpened(drawerView: View) {
                }

            })
        }

        left_navigation.run {

            setNavigationItemSelectedListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.nav_help_center ->
                        Toast.makeText(this@MainActivity, "帮助中心", Toast.LENGTH_SHORT).show()
                    R.id.nav_setting ->
                        Toast.makeText(this@MainActivity, "设置", Toast.LENGTH_SHORT).show()
                    R.id.nav_camera ->
                        Toast.makeText(this@MainActivity, "照相机", Toast.LENGTH_SHORT).show()
                    R.id.nav_gallery ->
                        Toast.makeText(this@MainActivity, "相册", Toast.LENGTH_SHORT).show()
                }
                toggle()
                false
            }
        }
    }

    private val onNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            return@OnNavigationItemSelectedListener when (item.itemId) {
                R.id.page_news -> {
                    naviTag(0)
                    true
                }
                R.id.page_video -> {
                    naviTag(1)
                    true
                }
                R.id.page_recommend -> {
                    naviTag(2)
                    true
                }
                R.id.page_address -> {
                    naviTag(3)
                    true
                }

                else -> {
                    false
                }

            }
        }

    private fun naviTag(position: Int) {

        vp_main.currentItem = position

        toolbar.run {
            tv_title.text = mTitles[position]
        }

    }
    open fun toggle() {
        val drawerLockMode = dl_drawer.getDrawerLockMode(GravityCompat.START)
        if (dl_drawer.isDrawerVisible(GravityCompat.START) && drawerLockMode != DrawerLayout.LOCK_MODE_LOCKED_OPEN) {
            dl_drawer.closeDrawer(GravityCompat.START)
        } else if (drawerLockMode != DrawerLayout.LOCK_MODE_LOCKED_CLOSED) {
            dl_drawer.openDrawer(GravityCompat.START)
        }
    }

    private var mExitTime: Long = 0

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if(!closeDrawableLayout()){
                if (System.currentTimeMillis().minus(mExitTime) <= 2000) {
                    finish()
                }else{
                    mExitTime = System.currentTimeMillis()
                    showToast(getString(R.string.exit_tip))
                }
                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    private fun closeDrawableLayout(): Boolean {
        return if (dl_drawer.isDrawerVisible(GravityCompat.START)) {
            dl_drawer.closeDrawer(GravityCompat.START)
            true
        } else
            false
    }

    override fun onBackPressed() {
        if (Jzvd.backPress()) {
            return
        }
        super.onBackPressed()
    }

    override fun onPause() {
        super.onPause()
        Jzvd.resetAllVideos()
    }
}


