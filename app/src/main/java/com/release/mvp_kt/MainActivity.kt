package com.release.mvp_kt

import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.internal.NavigationMenuItemView
import com.google.android.material.navigation.NavigationView
import com.orhanobut.logger.Logger
import com.release.mvp_kt.base.BaseMvpActivity
import com.release.mvp_kt.mvp.contract.MainContract
import com.release.mvp_kt.mvp.presenter.MainPresenter
import kotlinx.android.synthetic.main.activity_main.*

/**
 * @author Mr.release
 * @create 2019/7/10
 * @Describe
 */
class MainActivity : BaseMvpActivity<MainContract.View, MainContract.Presenter>(), MainContract.View {


    override fun createPresenter(): MainContract.Presenter = MainPresenter()

    override fun initLayoutID(): Int = R.layout.activity_main


    var mHeadImg: ImageView? = null

    override fun initView() {

        left_navigation.itemIconTintList = null
        mHeadImg = left_navigation.getHeaderView(0).findViewById<ImageView>(R.id.headImg)

        bottom_navigation.enableAnimation(false)
        val navController = (supportFragmentManager.findFragmentById(R.id.fragment_nav) as NavHostFragment).navController
        NavigationUI.setupWithNavController(bottom_navigation,navController)
    }

    override fun initListener() {

        dl_drawer.run {
            setScrimColor(resources.getColor(R.color.black_alpha_32))
            addDrawerListener(object: DrawerLayout.DrawerListener{
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

            setNavigationItemSelectedListener(object: NavigationView.OnNavigationItemSelectedListener {
                override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
                    when(menuItem.itemId){

                    }
                    return false
                }

            })
        }
    }
}


