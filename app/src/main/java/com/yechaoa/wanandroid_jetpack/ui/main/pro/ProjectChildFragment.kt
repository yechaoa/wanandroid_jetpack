package com.yechaoa.wanandroid_jetpack.ui.main.pro

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.yechaoa.wanandroid_jetpack.base.BaseVmFragment
import com.yechaoa.wanandroid_jetpack.databinding.FragmentProjectChildBinding
import com.yechaoa.wanandroid_jetpack.ui.adapter.ProjectChildAdapter
import com.yechaoa.wanandroid_jetpack.ui.detail.DetailActivity

/**
 * A simple [Fragment] subclass.
 */
class ProjectChildFragment : BaseVmFragment<FragmentProjectChildBinding, ProjectViewModel>() {

    companion object {
        const val CID: String = "cid"

        /**
         * 创建fragment
         */
        fun newInstance(cid: Int): ProjectChildFragment {
            val projectChildFragment = ProjectChildFragment()
            val bundle = Bundle()
            bundle.putInt(CID, cid)
            projectChildFragment.arguments = bundle
            return projectChildFragment
        }
    }

    private var mCid: Int = 0
    private lateinit var mAdapter: ProjectChildAdapter

    override fun viewModelClass(): Class<ProjectViewModel> {
        return ProjectViewModel::class.java
    }

    override fun getViewBinding(): FragmentProjectChildBinding {
        return FragmentProjectChildBinding.inflate(layoutInflater)
    }

    override fun initView() {
        initRecyclerView()
    }

    private fun initRecyclerView() {
        mAdapter = ProjectChildAdapter().apply {
            animationEnable = true
            setOnItemClickListener { _, _, position ->
                val intent = Intent(requireContext(), DetailActivity::class.java).apply {
                    putExtra(DetailActivity.WEB_URL, mAdapter.data[position].link)
                    putExtra(DetailActivity.WEB_TITLE, mAdapter.data[position].title)
                }
                startActivity(intent)
            }
            loadMoreModule.setOnLoadMoreListener {
                if (mCurrentSize < mTotalCount) {
                    loadMoreModule.loadMoreEnd(true)
                } else {
                    mCurrentPage++
                    mViewModel.getProjectChild(mCurrentPage, mCid)
                }
            }
        }
        mBinding.recyclerView.addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
        mBinding.recyclerView.adapter = mAdapter
    }

    override fun initData() {
        mCid = arguments?.getInt(CID)!!
        mViewModel.getProjectChild(mCurrentPage, mCid)
    }

    override fun observe() {
        super.observe()
        mViewModel.childList.observe(this, {
            mCurrentSize = it.size
            if (0 == mCurrentPage) {
                mAdapter.setList(it)
            } else {
                mAdapter.addData(it)
                mAdapter.loadMoreModule.loadMoreComplete()
            }
        })
    }

}
