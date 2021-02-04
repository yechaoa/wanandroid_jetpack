package com.yechaoa.wanandroid_jetpack.ui.collect

import com.yechaoa.wanandroid_jetpack.base.BaseRepository

/**
 * Created by yechaoa on 2021/2/4.
 * Describe :
 */
class CollectRepository : BaseRepository() {

    suspend fun getCollectList(page: Int) = apiService().getCollectList(page).data()

    suspend fun unCollect1(id: Int, originId: Int) = apiService().unCollect1(id, originId).data()

}