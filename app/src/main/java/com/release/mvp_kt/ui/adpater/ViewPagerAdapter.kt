package com.release.mvp_kt.ui.adpater

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

/**
 * @author Mr.release
 * @create 2019/6/24
 * @Describe
 */
class ViewPagerAdapter(val list: List<Fragment>, fm : FragmentManager) : FragmentPagerAdapter(fm){
    override fun getItem(p0: Int): Fragment {
        return list.get(p0)
    }

    override fun getCount(): Int {
        return list.size
    }

}