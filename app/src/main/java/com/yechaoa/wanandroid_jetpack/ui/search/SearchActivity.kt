package com.yechaoa.wanandroid_jetpack.ui.search

import android.content.Intent
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import com.yechaoa.wanandroid_jetpack.R
import com.yechaoa.wanandroid_jetpack.base.BaseVmActivity
import com.yechaoa.wanandroid_jetpack.data.bean.History
import com.yechaoa.wanandroid_jetpack.data.bean.Hotkey
import com.yechaoa.wanandroid_jetpack.data.room.HistoryDao
import com.yechaoa.wanandroid_jetpack.data.room.HistoryDatabase
import com.yechaoa.wanandroid_jetpack.databinding.ActivitySearchBinding
import com.yechaoa.wanandroid_jetpack.ui.adapter.ArticleAdapter
import com.yechaoa.wanandroid_jetpack.ui.detail.DetailActivity
import com.yechaoa.wanandroid_jetpack.util.randomColor
import com.yechaoa.yutilskt.*
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class SearchActivity : BaseVmActivity<ActivitySearchBinding, SearchViewModel>(ActivitySearchBinding::inflate) {

    private lateinit var mArticleAdapter: ArticleAdapter
    private lateinit var mKey: String
    private var mPosition: Int = 0

    override fun viewModelClass(): Class<SearchViewModel> {
        return SearchViewModel::class.java
    }

    override fun initView() {
        initRecyclerView()
        setSupportActionBar(mBinding.toolbar)
    }

    private fun initRecyclerView() {
        mArticleAdapter = ArticleAdapter().apply {
            animationEnable = true
            setOnItemClickListener { _, _, position ->
                val intent = Intent(this@SearchActivity, DetailActivity::class.java).apply {
                    putExtra(DetailActivity.WEB_URL, mArticleAdapter.data[position].link)
                    putExtra(DetailActivity.WEB_TITLE, mArticleAdapter.data[position].title)
                }
                startActivity(intent)
            }
            setOnItemChildClickListener { _, _, position ->
                mPosition = position
                if (data[position].collect) {
                    mViewModel.unCollect(data[position].id)
                } else {
                    mViewModel.collect(data[position].id)
                }
            }
            loadMoreModule.setOnLoadMoreListener {
                mBinding.recyclerView.postDelayed({
                    if (mCurrentSize < mTotalCount) {
                        mArticleAdapter.loadMoreModule.loadMoreEnd(true)
                    } else {
                        mCurrentPage++
                        mViewModel.getArticleList(mCurrentPage, mKey)
                    }
                }, 500)
            }
        }

        mBinding.recyclerView.adapter = mArticleAdapter
    }

    private lateinit var mHistoryDao: HistoryDao

    override fun initData() {
        super.initData()
        mHistoryDao = HistoryDatabase.getInstance(this).historyDao()
        mViewModel.getHotkey()
        getSearchHistory()
    }

    private fun getSearchHistory() {
        MainScope().launch(Dispatchers.IO) {
            mHistoryDao.getAll().collect {
                withContext(Dispatchers.Main) {
                    if (it.isNotEmpty()) {
                        mBinding.llHistory.visibility = View.VISIBLE
                        setHistory(it)
                    } else {
                        mBinding.llHistory.visibility = View.GONE
                    }
                }
            }
        }
    }

    private fun saveSearchHistory(text: String) {
        MainScope().launch(Dispatchers.IO) {
            val id = mHistoryDao.queryIdByName(text)
            if (null == id) {
                mHistoryDao.insert(History(null, text, TimeUtil.dateAndTime))
            } else {
                mHistoryDao.update(History(id, text, TimeUtil.dateAndTime))
            }
        }
    }

    private fun cleanHistory() {
        MainScope().launch(Dispatchers.IO) {
            mHistoryDao.deleteAll()
        }
    }

    private lateinit var mEditText: EditText

    /**
     * 添加SearchView
     * SearchView使用参考：https://blog.csdn.net/yechaoa/article/details/80658940
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        //引用menu文件
        menuInflater.inflate(R.menu.menu_search, menu)

        //找到SearchView并配置相关参数
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        searchView.apply {
            //搜索图标是否显示在搜索框内
            setIconifiedByDefault(true)
            //设置搜索框展开时是否显示提交按钮，可不显示
            isSubmitButtonEnabled = true
            //让键盘的回车键设置成搜索
            imeOptions = EditorInfo.IME_ACTION_SEARCH
            //搜索框是否展开，false表示展开
            isIconified = false
            //获取焦点
            isFocusable = true
            requestFocusFromTouch()
            //设置提示词
            queryHint = "请输入关键字"
        }

        //设置输入框文字颜色
        mEditText = searchView.findViewById(androidx.appcompat.R.id.search_src_text) as EditText
        mEditText.setHintTextColor(ContextCompat.getColor(this, R.color.white30))
        mEditText.setTextColor(ContextCompat.getColor(this, R.color.white))

        //设置搜索文本监听
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            // 当搜索内容改变时触发该方法
            override fun onQueryTextChange(newText: String): Boolean {
                //当没有输入任何内容的时候显示搜索热词，看实际需求
                mBinding.llHotkey.visibility = View.VISIBLE
                mBinding.recyclerView.visibility = View.GONE
                return false
            }

            // 当点击搜索按钮时触发该方法
            override fun onQueryTextSubmit(query: String): Boolean {
                LogUtil.i("aaa", "搜索内容===$query")
                mKey = query
                saveSearchHistory(mKey)
                mCurrentPage = 0 //重置分页，避免二次加载分页混乱
                //搜索请求
                mViewModel.getArticleList(mCurrentPage, mKey)
                //清除焦点，收软键盘
                searchView.clearFocus()
                return false
            }
        })

        return super.onCreateOptionsMenu(menu)
    }

    override fun observe() {
        super.observe()
        mViewModel.hotkeyList.observe(this, {
            setHotkey(it)
        })

        mViewModel.articleList.observe(this, {
            mBinding.llHotkey.visibility = View.GONE
            mBinding.recyclerView.visibility = View.VISIBLE

            mCurrentSize = it.size
            if (0 == mCurrentPage) {
                if (it.isEmpty()) {
                    mArticleAdapter.setList(null)
                    mArticleAdapter.setEmptyView(R.layout.layout_empty_view)
                } else {
                    mArticleAdapter.setList(it)
                }
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

    private fun setHotkey(list: MutableList<Hotkey>) {
        mBinding.flowLayoutHot.adapter = object : TagAdapter<Hotkey>(list) {
            override fun getView(parent: FlowLayout, position: Int, s: Hotkey): View {
                val tvTag = LayoutInflater.from(this@SearchActivity).inflate(
                    R.layout.item_navi, mBinding.flowLayoutHot, false
                ) as TextView
                tvTag.text = s.name
                tvTag.setTextColor(randomColor())
                return tvTag
            }
        }
        //设置点击事件
        mBinding.flowLayoutHot.setOnTagClickListener { _, position, _ ->
            YUtils.closeSoftKeyboard()
            mKey = list[position].name
            //填充搜索框
            mEditText.setText(mKey)
            saveSearchHistory(mKey)
            mCurrentPage = 0 //重置分页，避免二次加载分页混乱
            mViewModel.getArticleList(mCurrentPage, mKey)
            return@setOnTagClickListener true
        }
    }

    private fun setHistory(list: List<History>) {
        mBinding.flowLayoutHistory.adapter = object : TagAdapter<History>(list) {
            override fun getView(parent: FlowLayout, position: Int, s: History): View {
                val tvTag = LayoutInflater.from(this@SearchActivity).inflate(
                    R.layout.item_navi, mBinding.flowLayoutHistory, false
                ) as TextView
                tvTag.text = s.name
                tvTag.setTextColor(randomColor())
                return tvTag
            }
        }
        //设置点击事件
        mBinding.flowLayoutHistory.setOnTagClickListener { _, position, _ ->
            YUtils.closeSoftKeyboard()
            mKey = list[position].name!!
            //填充搜索框
            mEditText.setText(mKey)
            saveSearchHistory(mKey)
            mCurrentPage = 0 //重置分页，避免二次加载分页混乱
            mViewModel.getArticleList(mCurrentPage, mKey)
            return@setOnTagClickListener true
        }
    }

    override fun setListener() {
        super.setListener()
        mBinding.toolbar.setNavigationOnClickListener {
            super.onBackPressed()
        }

        mBinding.ivDelete.setOnClickListener {
            cleanHistory()
            mBinding.llHistory.visibility = View.GONE
        }
    }
}
