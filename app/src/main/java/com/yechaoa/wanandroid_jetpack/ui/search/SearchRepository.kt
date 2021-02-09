package com.yechaoa.wanandroid_jetpack.ui.search

import com.yechaoa.wanandroid_jetpack.base.BaseRepository

/**
 * Created by yechaoa on 2021/2/9.
 * Describe :
 */
class SearchRepository : BaseRepository() {

    suspend fun getHotkey() = apiService().getHotkey().data()

    suspend fun getSearchList(page: Int, key: String) = apiService().getSearchList(page, key).data()

    suspend fun collect(id: Int) = apiService().collect(id)

    suspend fun unCollectByArticle(id: Int) = apiService().unCollectByArticle(id)

}