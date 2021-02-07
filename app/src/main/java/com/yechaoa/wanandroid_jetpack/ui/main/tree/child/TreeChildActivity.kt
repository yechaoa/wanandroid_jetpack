package com.yechaoa.wanandroid_jetpack.ui.main.tree.child

import com.yechaoa.wanandroid_jetpack.base.BaseActivity
import com.yechaoa.wanandroid_jetpack.data.bean.Tree
import com.yechaoa.wanandroid_jetpack.databinding.ActivityTreeChildBinding
import com.yechaoa.wanandroid_jetpack.ui.adapter.CommonViewPagerAdapter

class TreeChildActivity : BaseActivity<ActivityTreeChildBinding>() {

    companion object {
        const val TITLE: String = "title"
        const val CID: String = "cid"
        const val POSITION: String = "position"
    }

    override fun getViewBinding(): ActivityTreeChildBinding {
        return ActivityTreeChildBinding.inflate(layoutInflater)
    }

    override fun init() {
        super.init()
        //初始化toolbar
        val title = intent.getStringExtra(TITLE as String?)
        mBinding.tvTitle.text = title

        //title集合
        val childList: ArrayList<Tree.Children> = intent.getSerializableExtra(CID) as ArrayList<Tree.Children>
        val titles = java.util.ArrayList<String>()
        for (i in childList.indices) {
            titles.add(childList[i].name)
        }

        //动态创建fragment
        val commonViewPagerAdapter = CommonViewPagerAdapter(supportFragmentManager, titles)
        for (i in titles.indices) {
            commonViewPagerAdapter.addFragment(TreeChildFragment.newInstance(childList[i].id))
        }

        mBinding.viewPager.adapter = commonViewPagerAdapter

        //设置当前显示位置
        val index = intent.getIntExtra(POSITION, 0)
        mBinding.viewPager.currentItem = index
        mBinding.tabLayout.setupWithViewPager(mBinding.viewPager)

        setListener()
    }

    private fun setListener() {
        mBinding.toolbar.setNavigationOnClickListener {
            super.onBackPressed()
        }
    }
}
