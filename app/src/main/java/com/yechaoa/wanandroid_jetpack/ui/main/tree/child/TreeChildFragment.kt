package com.yechaoa.wanandroid_jetpack.ui.main.tree.child


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.yechaoa.wanandroid_jetpack.base.BaseVmFragment
import com.yechaoa.wanandroid_jetpack.data.bean.Article
import com.yechaoa.wanandroid_jetpack.databinding.FragmentChildBinding
import com.yechaoa.wanandroid_jetpack.ui.adapter.ArticleAdapter
import com.yechaoa.wanandroid_jetpack.ui.detail.DetailActivity
import com.yechaoa.yutilskt.ToastUtil

/**
 * A simple [Fragment] subclass.
 */
class TreeChildFragment : BaseVmFragment<FragmentChildBinding, TreeChildViewModel>() {

    companion object {
        const val CID: String = "cid"

        /**
         * 创建fragment
         */
        fun newInstance(cid: Int): TreeChildFragment {
            val projectChildFragment = TreeChildFragment()
            val bundle = Bundle()
            bundle.putInt(CID, cid)
            projectChildFragment.arguments = bundle
            return projectChildFragment
        }
    }

    private var mCid: Int = 0
    private lateinit var mArticleAdapter: ArticleAdapter
    private var mPosition: Int = 0

    override fun viewModelClass(): Class<TreeChildViewModel> {
        return TreeChildViewModel::class.java
    }

    override fun getViewBinding(): FragmentChildBinding {
        return FragmentChildBinding.inflate(layoutInflater)
    }

    override fun initView() {
        initRecyclerView()
    }

    private fun initRecyclerView() {
        mArticleAdapter = ArticleAdapter().apply {
            //开启加载动画
            animationEnable = true
            //item点击
            setOnItemClickListener { _, _, position ->
                val intent = Intent(requireContext(), DetailActivity::class.java).apply {
                    putExtra(DetailActivity.WEB_URL, mArticleAdapter.data[position].link)
                    putExtra(DetailActivity.WEB_TITLE, mArticleAdapter.data[position].title)
                }
                startActivity(intent)
            }

            //item子view点击
            setOnItemChildClickListener { _, _, position ->
                mPosition = position
                if (data[position].collect) {
                    mViewModel.unCollect(data[position].id)
                } else {
                    mViewModel.collect(data[position].id)
                }
            }
            //加载更多
            loadMoreModule.setOnLoadMoreListener {
                if (mCurrentSize < mTotalCount) {
                    mArticleAdapter.loadMoreModule.loadMoreEnd(true)
                } else {
                    mCurrentPage++
                    mViewModel.getTreeChild(mCurrentPage, mCid)
                }
            }
        }

        mBinding.recyclerView.adapter = mArticleAdapter
    }

    override fun initData() {
        mCid = arguments?.getInt(CID)!!
        mViewModel.getTreeChild(mCurrentPage, mCid)
    }

    override fun observe() {
        super.observe()
        mViewModel.articleList.observe(this, {
            mCurrentSize = it.size
            if (0 == mCurrentPage) {
                mArticleAdapter.setList(it)
            } else {
                mArticleAdapter.addData(it)
                mArticleAdapter.loadMoreModule.loadMoreComplete()
            }
        })

        mViewModel.collectState.observe(this, {
            if (it) {
                ToastUtil.show("收藏成功")
                mArticleAdapter.data[mPosition].collect = true
                mArticleAdapter.notifyItemChanged(mPosition)
            }
        })

        mViewModel.unCollectState.observe(this, {
            if (it) {
                ToastUtil.show("取消成功")
                mArticleAdapter.data[mPosition].collect = false
                mArticleAdapter.notifyItemChanged(mPosition)
            }
        })
    }

}
