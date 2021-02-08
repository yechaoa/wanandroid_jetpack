package com.yechaoa.wanandroid_jetpack.ui.main.pro

import com.yechaoa.wanandroid_jetpack.base.BaseVmFragment
import com.yechaoa.wanandroid_jetpack.databinding.FragmentProjectBinding
import com.yechaoa.wanandroid_jetpack.ui.adapter.CommonViewPagerAdapter
import java.util.ArrayList

class ProjectFragment : BaseVmFragment<FragmentProjectBinding, ProjectViewModel>() {

    override fun getViewBinding(): FragmentProjectBinding {
        return FragmentProjectBinding.inflate(layoutInflater)
    }

    override fun viewModelClass(): Class<ProjectViewModel> {
        return ProjectViewModel::class.java
    }

    override fun initView() {
        mBinding.tabLayout.setupWithViewPager(mBinding.viewPager)
    }

    override fun initData() {
        super.initData()
        mViewModel.getProject()
    }

    override fun observe() {
        super.observe()
        mViewModel.proList.observe(this, {
            //得到标题集合
            val titles: MutableList<String> = ArrayList()
            for (i in it.indices) {
                titles.add(it[i].name)
            }

            //创建Fragment集合 并设置为ViewPager
            val commonViewPagerAdapter = CommonViewPagerAdapter(childFragmentManager, titles)
            for (i in titles.indices) {
                commonViewPagerAdapter.addFragment(ProjectChildFragment.newInstance(it[i].id))
            }
            mBinding.viewPager.adapter = commonViewPagerAdapter
            mBinding.viewPager.currentItem = 0
        })
    }

}