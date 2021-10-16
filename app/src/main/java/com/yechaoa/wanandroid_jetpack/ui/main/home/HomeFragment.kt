package com.yechaoa.wanandroid_jetpack.ui.main.home

import android.content.Intent
import com.yechaoa.wanandroid_jetpack.base.BaseVmFragment
import com.yechaoa.wanandroid_jetpack.databinding.FragmentHomeBinding
import com.yechaoa.wanandroid_jetpack.ui.adapter.ArticleAdapter
import com.yechaoa.wanandroid_jetpack.ui.adapter.BannerImageAdapter
import com.yechaoa.wanandroid_jetpack.ui.detail.DetailActivity
import com.yechaoa.yutilskt.DisplayUtil
import com.yechaoa.yutilskt.ToastUtil
import com.youth.banner.indicator.CircleIndicator
import com.youth.banner.transformer.*
import java.util.*
import kotlin.math.roundToInt

class HomeFragment : BaseVmFragment<FragmentHomeBinding, HomeViewModel>(FragmentHomeBinding::inflate) {

    private lateinit var mArticleAdapter: ArticleAdapter
    private var mPosition = -1

    override fun viewModelClass(): Class<HomeViewModel> = HomeViewModel::class.java

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
                mBinding.recyclerView.postDelayed({
                    if (mCurrentSize < mTotalCount) {
                        mArticleAdapter.loadMoreModule.loadMoreEnd(true)
                    } else {
                        mCurrentPage++
                        mViewModel.getArticleList(mCurrentPage)
                    }
                }, 500)
            }
        }

        mBinding.recyclerView.adapter = mArticleAdapter
    }

    override fun initData() {
        super.initData()
        mViewModel.getBanner()
        mViewModel.getArticleList(mCurrentPage)
    }

    override fun observe() {
        super.observe()
        mViewModel.bannerList.observe(this, { bannerList ->
            //动态设置高度
            val layoutParams = mBinding.banner.layoutParams
            layoutParams.height = (DisplayUtil.getScreenWidth() / 1.99).roundToInt()

            mBinding.banner.apply {
                addBannerLifecycleObserver(this@HomeFragment)
                setBannerGalleryEffect(8, 5)
                setPageTransformer(ScaleInTransformer())
                addPageTransformer(AlphaPageTransformer())
                indicator = CircleIndicator(requireContext())
                adapter = BannerImageAdapter(bannerList)
                setDatas(bannerList)
                start()
            }
            mBinding.banner.setOnBannerListener { _, position ->
                val intent = Intent(requireContext(), DetailActivity::class.java).apply {
                    putExtra(DetailActivity.WEB_URL, bannerList[position].url)
                    putExtra(DetailActivity.WEB_TITLE, bannerList[position].title)
                }
                startActivity(intent)
            }
        })

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