package com.yechaoa.wanandroid_jetpack.ui.main.home

import com.yechaoa.wanandroid_jetpack.base.BaseVmFragment
import com.yechaoa.wanandroid_jetpack.databinding.FragmentHomeBinding
import com.yechaoa.wanandroid_jetpack.ui.adapter.ArticleAdapter
import com.yechaoa.wanandroid_jetpack.ui.adapter.BannerImageAdapter
import com.yechaoa.yutilskt.DisplayUtil
import com.youth.banner.indicator.CircleIndicator
import com.youth.banner.transformer.*
import java.util.*
import kotlin.math.roundToInt

class HomeFragment : BaseVmFragment<FragmentHomeBinding, HomeViewModel>() {

    companion object {
        private const val TOTAL_COUNTER = 20//每次加载数量
        private var CURRENT_SIZE = 0//当前加载数量
        private var CURRENT_PAGE = 0//当前加载页数
    }

    private lateinit var mArticleAdapter: ArticleAdapter

    override fun getViewBinding(): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(layoutInflater)
    }

    override fun viewModelClass(): Class<HomeViewModel> = HomeViewModel::class.java

    override fun initView() {
        initRecyclerView()
    }

    private fun initRecyclerView() {
        mArticleAdapter = ArticleAdapter().apply {
            //开启加载动画
            animationEnable = true
            //item点击
            setOnItemClickListener { adapter, view, position ->
//                val intent = Intent(requireContext(), DetailActivity::class.java).apply {
//                    putExtra(DetailActivity.WEB_URL, mDataList[position].link)
//                    putExtra(DetailActivity.WEB_TITLE, mDataList[position].title)
//                }
//                startActivity(intent)
            }

            //item子view点击
            setOnItemChildClickListener { adapter, view, position ->
//                mPosition = position
//                if (mDataList[position].collect) {
//                    mHomePresenter.unCollect(mDataList[position].id)
//                } else {
//                    mHomePresenter.collect(mDataList[position].id)
//                }
            }
            //加载更多
            loadMoreModule.setOnLoadMoreListener {
                mBinding.recyclerView.postDelayed({
                    if (CURRENT_SIZE < TOTAL_COUNTER) {
                        mArticleAdapter.loadMoreModule.loadMoreEnd(true)
                    } else {
                        CURRENT_PAGE++
                        mViewModel.getArticleList(CURRENT_PAGE)
                    }
                }, 1000)
            }
        }

        mBinding.recyclerView.adapter = mArticleAdapter
    }

    override fun initData() {
        super.initData()
        mViewModel.getBanner()
        mViewModel.getArticleList(CURRENT_PAGE)
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
            mBinding.banner.setOnBannerListener { data, position ->
//                val intent = Intent(requireContext(), DetailActivity::class.java).apply {
//                    putExtra(DetailActivity.WEB_URL, bannerList[position].url)
//                    putExtra(DetailActivity.WEB_TITLE, bannerList[position].title)
//                }
//                startActivity(intent)
            }
        })

        mViewModel.articleList.observe(this, {
            CURRENT_SIZE = it.size
            if (0 == CURRENT_PAGE) {
                mArticleAdapter.setList(it)
            } else {
                mArticleAdapter.addData(it)
                mArticleAdapter.loadMoreModule.loadMoreComplete()
            }
        })
    }

}