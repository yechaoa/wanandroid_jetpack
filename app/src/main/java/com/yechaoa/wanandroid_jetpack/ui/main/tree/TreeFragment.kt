package com.yechaoa.wanandroid_jetpack.ui.main.tree

import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.yechaoa.wanandroid_jetpack.base.BaseVmFragment
import com.yechaoa.wanandroid_jetpack.databinding.FragmentTreeBinding
import com.yechaoa.wanandroid_jetpack.ui.adapter.TreeAdapter
import com.zhy.view.flowlayout.FlowLayout

class TreeFragment : BaseVmFragment<FragmentTreeBinding, TreeViewModel>() {

    private var mPosition: Int = 0

    override fun getViewBinding(): FragmentTreeBinding {
        return FragmentTreeBinding.inflate(layoutInflater)
    }

    override fun viewModelClass(): Class<TreeViewModel> {
        return TreeViewModel::class.java
    }

    override fun initView() {
        initSwipeRefreshLayout()
        initRecyclerView()
    }

    private lateinit var mTreeAdapter: TreeAdapter

    private fun initRecyclerView() {
        mTreeAdapter = TreeAdapter().apply {
            setOnItemClickListener { _, _, position ->
                mPosition = position

                //先重置再赋值，实现类似单选的效果
                if (data[position].isShow) {
                    for (i in data.indices) {
                        data[i].isShow = false
                    }
                } else {
                    for (i in data.indices) {
                        data[i].isShow = false
                    }
                    data[position].isShow = true
                }
                //notifyItemChanged(position)
                notifyDataSetChanged()
            }

            //子view标签点击事件
            setOnItemTagClickListener(object : TreeAdapter.OnItemTagClickListener {
                override fun onItemTagClick(view: View?, position: Int, parent: FlowLayout?): Boolean {
//                    val intent = Intent(requireContext(), TreeChildActivity::class.java).apply {
//                        putExtra(TreeChildActivity.TITLE, mTreeAdapter.data[mPosition].name)
//                        putExtra(TreeChildActivity.CID, mTreeAdapter.data[mPosition].children)
//                        putExtra(TreeChildActivity.POSITION, position)
//                    }
//                    startActivity(intent)
                    return true
                }
            })
        }
        mBinding.recyclerView.addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
        mBinding.recyclerView.adapter = mTreeAdapter
    }

    private fun initSwipeRefreshLayout() {
        mBinding.swipeRefresh.setColorSchemeResources(
            android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light
        )
        mBinding.swipeRefresh.setOnRefreshListener {
            mBinding.swipeRefresh.postDelayed({
                mViewModel.getTree()
                mBinding.swipeRefresh.isRefreshing = false
            }, 1500)
        }
    }

    override fun initData() {
        super.initData()
        mViewModel.getTree()
    }

    override fun observe() {
        super.observe()
        mViewModel.treeList.observe(this, {
            mTreeAdapter.setList(it)
        })
    }

}