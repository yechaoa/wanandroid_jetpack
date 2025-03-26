package com.yechaoa.wanandroid_jetpack.ui.collect

import android.content.Intent
import com.yechaoa.wanandroid_jetpack.R
import com.yechaoa.wanandroid_jetpack.base.BaseVmActivity
import com.yechaoa.wanandroid_jetpack.databinding.ActivityCollectBinding
import com.yechaoa.wanandroid_jetpack.ui.adapter.CollectAdapter
import com.yechaoa.wanandroid_jetpack.ui.detail.DetailActivity
import com.yechaoa.yutilskt.ToastUtil

class CollectActivity : BaseVmActivity<ActivityCollectBinding, CollectViewModel>(ActivityCollectBinding::inflate) {

    private lateinit var mCollectAdapter: CollectAdapter
    private var mPosition = -1

    override fun viewModelClass(): Class<CollectViewModel> = CollectViewModel::class.java

    override fun initView() {
        initSwipeRefreshLayout()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        mCollectAdapter = CollectAdapter().apply {
            //开启加载动画
            animationEnable = true
            //item点击
            setOnItemClickListener { _, _, position ->
                val intent = Intent(this@CollectActivity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.WEB_URL, data[position].link)
                intent.putExtra(DetailActivity.WEB_TITLE, data[position].title)
                startActivity(intent)
            }

            //item子view点击
            setOnItemChildClickListener { _, _, position ->
                mPosition = position
                val oid: Int = if (-1 < data[position].originId) data[position].originId else -1
                mViewModel.unCollectByCollect(data[position].id, oid)
            }

            //加载更多
            loadMoreModule.setOnLoadMoreListener {
                if (mCurrentSize < mTotalCount) {
                    mCollectAdapter.loadMoreModule.loadMoreEnd(true)
                } else {
                    mCurrentPage++
                    mViewModel.getCollectList(mCurrentPage)
                }
            }
        }

        mBinding.recyclerView.adapter = mCollectAdapter
    }

    private fun initSwipeRefreshLayout() {
        mBinding.swipeRefresh.setColorSchemeResources(
            android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light
        )
        mBinding.swipeRefresh.setOnRefreshListener {
            mBinding.swipeRefresh.postDelayed({
                mCurrentPage = 0
                mViewModel.getCollectList(mCurrentPage)
                mBinding.swipeRefresh.isRefreshing = false
            }, 1000)
        }
    }

    override fun initData() {
        super.initData()
        mViewModel.getCollectList(mCurrentPage)
    }

    override fun observe() {
        super.observe()

        mViewModel.collectList.observe(this, {
            mCurrentSize = it.size
            if (0 == mCurrentPage) {
                if (it.isEmpty()) {
                    mCollectAdapter.setList(null)
                    mCollectAdapter.setEmptyView(R.layout.layout_empty_view)
                } else {
                    mCollectAdapter.setList(it)
                }
            } else {
                mCollectAdapter.addData(it)
                mCollectAdapter.loadMoreModule.loadMoreComplete()
            }
        })

        mViewModel.unCollectState.observe(this, {
            if (it) {
                ToastUtil.show("取消成功")
                mCollectAdapter.data.removeAt(mPosition)
                mCollectAdapter.notifyItemRemoved(mPosition)
                if (mCollectAdapter.data.size == 0) {
                    mCollectAdapter.setEmptyView(R.layout.layout_empty_view)
                }
            }
        })

    }

    override fun setListener() {
        super.setListener()
        mBinding.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}
