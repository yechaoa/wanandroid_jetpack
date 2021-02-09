package com.yechaoa.wanandroid_jetpack.ui.search

import androidx.lifecycle.MutableLiveData
import com.yechaoa.wanandroid_jetpack.base.BaseViewModel
import com.yechaoa.wanandroid_jetpack.data.bean.Article
import com.yechaoa.wanandroid_jetpack.data.bean.Banner
import com.yechaoa.wanandroid_jetpack.data.bean.Hotkey

class SearchViewModel : BaseViewModel() {

    private val repository by lazy { SearchRepository() }

    val hotkeyList = MutableLiveData<MutableList<Hotkey>>()

    fun getHotkey() {
        launch(
            block = {
                hotkeyList.value = repository.getHotkey()
            }
        )
    }

    val articleList = MutableLiveData<MutableList<Article.ArticleDetail>>()

    fun getArticleList(page: Int, key: String) {
        launch(
            block = {
                articleList.value = repository.getSearchList(page, key).datas
            }
        )
    }

    val collectState = MutableLiveData<Boolean>()

    fun collect(id: Int) {
        launch(
            block = {
                collectState.value = 0 == repository.collect(id).code()
            }
        )
    }

    val unCollectState = MutableLiveData<Boolean>()

    fun unCollect(id: Int) {
        launch(
            block = {
                unCollectState.value = 0 == repository.unCollectByArticle(id).code()
            }
        )
    }

}