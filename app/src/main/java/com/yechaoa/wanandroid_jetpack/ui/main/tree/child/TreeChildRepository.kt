package com.yechaoa.wanandroid_jetpack.ui.main.tree.child

import com.yechaoa.wanandroid_jetpack.base.BaseRepository

/**
 * Created by yechaoa on 2021/2/7.
 * Describe :
 */
class TreeChildRepository : BaseRepository() {

    suspend fun getTreeChild(page: Int, cid: Int) = apiService().getTreeChild(page, cid).data()

    suspend fun collect(id: Int) = apiService().collect(id)

    suspend fun unCollectByArticle(id: Int) = apiService().unCollectByArticle(id)

}