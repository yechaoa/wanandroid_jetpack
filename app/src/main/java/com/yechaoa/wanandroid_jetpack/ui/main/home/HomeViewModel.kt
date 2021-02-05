package com.yechaoa.wanandroid_jetpack.ui.main.home

import androidx.lifecycle.MutableLiveData
import com.yechaoa.wanandroid_jetpack.base.BaseViewModel
import com.yechaoa.wanandroid_jetpack.data.bean.Article
import com.yechaoa.wanandroid_jetpack.data.bean.Banner

class HomeViewModel : BaseViewModel() {

    private val repository by lazy { HomeRepository() }

    val bannerList = MutableLiveData<MutableList<Banner>>()

    fun getBanner() {
        launch(
            block = {
                bannerList.value = repository.getBanner()
            }
        )
    }

    val articleList = MutableLiveData<MutableList<Article.ArticleDetail>>()

    fun getArticleList(page: Int) {
        launch(
            block = {
                articleList.value = repository.getArticleList(page).datas
            }
        )
    }

}