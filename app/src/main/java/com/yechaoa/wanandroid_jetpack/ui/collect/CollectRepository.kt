package com.yechaoa.wanandroid_jetpack.ui.collect

import com.yechaoa.wanandroid_jetpack.base.BaseRepository

/**
 * Created by yechaoa on 2021/2/4.
 * Describe :
 */
class CollectRepository : BaseRepository() {

    suspend fun getCollectList(page: Int) = apiService().getCollectList(page).data()

    suspend fun unCollectByCollect(id: Int, originId: Int) = apiService().unCollectByCollect(id, originId)

}