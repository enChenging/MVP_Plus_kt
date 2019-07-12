package com.release.mvp_kt.base


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter

import java.util.ArrayList
import java.util.Arrays
import java.util.Collections

/**
 * Created by long on 2016/6/2.
 * ViewPager适配器
 */
class ViewPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    private var mTitles: MutableList<String>
    private var fragments: MutableList<Fragment>

    init {
        fragments = ArrayList()
        mTitles = ArrayList()
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mTitles[position]
    }

    override fun getItemPosition(`object`: Any): Int {
        return PagerAdapter.POSITION_NONE
    }

    fun setItems(fragments: MutableList<Fragment>, mTitles: MutableList<String>) {
        this.fragments = fragments
        this.mTitles = mTitles
        notifyDataSetChanged()
    }

    fun setItems(fragments: MutableList<Fragment>, mTitles: Array<String>) {
        this.fragments = fragments
        this.mTitles = listOf(*mTitles) as MutableList<String>
        notifyDataSetChanged()
    }

    fun addItem(fragment: Fragment, title: String) {
        fragments.add(fragment)
        mTitles.add(title)
        notifyDataSetChanged()
    }

    fun delItem(position: Int) {
        mTitles.removeAt(position)
        fragments.removeAt(position)
        notifyDataSetChanged()
    }

    fun delItem(title: String): Int {
        val index = mTitles.indexOf(title)
        if (index != -1) {
            delItem(index)
        }
        return index
    }

    fun swapItems(fromPos: Int, toPos: Int) {
        Collections.swap(mTitles, fromPos, toPos)
        Collections.swap(fragments, fromPos, toPos)
        notifyDataSetChanged()
    }

    fun modifyTitle(position: Int, title: String) {
        mTitles[position] = title
        notifyDataSetChanged()
    }
}
