package com.yechaoa.wanandroid_jetpack.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import java.util.*

/**
 * Created by yechaoa on 2021/2/4.
 * Describe :
 */
class CommonViewPagerAdapter : FragmentPagerAdapter {

    private var mTitles: List<String>? = null
    private var mFragments: MutableList<Fragment> = ArrayList()

    constructor(fm: FragmentManager, titles: List<String>?) : super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        mTitles = titles
    }

    constructor(fm: FragmentManager) : super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)

    fun addFragment(fragment: Fragment) {
        mFragments.add(fragment)
    }

    override fun getItem(position: Int): Fragment {
        return mFragments[position]
    }

    override fun getCount(): Int {
        return mFragments.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return mTitles!![position]
    }

}
