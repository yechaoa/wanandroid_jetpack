package com.yechaoa.wanandroid_jetpack.ui.main.home

import com.yechaoa.wanandroid_jetpack.base.BaseRepository

/**
 * Created by yechaoa on 2021/2/5.
 * Describe :
 */
class HomeRepository : BaseRepository() {

    suspend fun getBanner() = apiService().getBanner().data()

    suspend fun getArticleList(page: Int) = apiService().getArticleList(page).data()

    suspend fun collect(id: Int) = apiService().collect(id)

    suspend fun unCollectByArticle(id: Int) = apiService().unCollectByArticle(id)

}