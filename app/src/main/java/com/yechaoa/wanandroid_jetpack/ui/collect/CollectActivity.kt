package com.yechaoa.wanandroid_jetpack.ui.collect

import android.content.Intent
import androidx.appcompat.app.AlertDialog
import com.yechaoa.wanandroid_jetpack.base.BaseVmActivity
import com.yechaoa.wanandroid_jetpack.data.bean.CollectDetail
import com.yechaoa.wanandroid_jetpack.databinding.ActivityCollectBinding
import com.yechaoa.wanandroid_jetpack.ui.adapter.CollectAdapter
import com.yechaoa.wanandroid_jetpack.ui.login.LoginActivity

class CollectActivity : BaseVmActivity<ActivityCollectBinding, CollectViewModel>() {

    private lateinit var mCollectAdapter: CollectAdapter
    private lateinit var mDataList: MutableList<CollectDetail>
    private var mPosition: Int = 0

    companion object {
        private const val TOTAL_COUNTER = 20//每次加载数量
        private var CURRENT_SIZE = 0//当前加载数量
        private var CURRENT_PAGE = 0//当前加载页数
    }

    override fun viewModelClass(): Class<CollectViewModel> = CollectViewModel::class.java

    override fun getViewBinding(): ActivityCollectBinding {
        return ActivityCollectBinding.inflate(layoutInflater)
    }

    override fun initView() {
        initSwipeRefreshLayout()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        mCollectAdapter = CollectAdapter().apply {
            //开启加载动画
            animationEnable = true
            //item点击
            setOnItemClickListener { adapter, view, position ->
//                val intent = Intent(this, DetailActivity::class.java)
//                intent.putExtra(DetailActivity.WEB_URL, mDataList[position].link)
//                intent.putExtra(DetailActivity.WEB_TITLE, mDataList[position].title)
//                startActivity(intent)
            }

            //item子view点击
            setOnItemChildClickListener { adapter, view, position ->
                mPosition = position
                val oid: Int = if (-1 < mDataList[position].originId)
                    mDataList[position].originId
                else -1
                mViewModel.unCollect1(mDataList[position].id, oid)
            }
            //加载更多
            loadMoreModule.setOnLoadMoreListener {
                if (CURRENT_SIZE < TOTAL_COUNTER) {
                    mCollectAdapter.loadMoreModule.loadMoreEnd(true)
                } else {
                    CURRENT_PAGE++
                    mViewModel.getCollectList(CURRENT_PAGE)
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
                CURRENT_PAGE = 0
                mViewModel.getCollectList(CURRENT_PAGE)
                mBinding.swipeRefresh.isRefreshing = false
            }, 1000)
        }
    }

    override fun initData() {
        super.initData()
        mViewModel.getCollectList(CURRENT_PAGE)
    }

    override fun observe() {
        super.observe()

        mViewModel.collectList.observe(this, {
            CURRENT_SIZE = it.size
            mDataList = it
            mCollectAdapter.setList(it)
        })
    }

//    override fun login(msg: String) {
//        showLoginDialog(msg)
//    }

    private fun showLoginDialog(msg: String) {
        val builder = AlertDialog.Builder(this@CollectActivity)
        builder.setTitle("提示")
        builder.setMessage(msg)
        builder.setPositiveButton("确定") { _, _ ->
            startActivity(Intent(this, LoginActivity::class.java))
        }
        builder.setNegativeButton("取消") { _, _ ->
            finish()
        }
        builder.create().show()
    }

//    override fun unCollect(msg: String) {
//        ToastUtil.show(msg)
//        mCollectAdapter.remove(mPosition)
//    }

}
