package com.yechaoa.wanandroid_jetpack.ui.main.tree

import com.yechaoa.wanandroid_jetpack.base.BaseRepository

/**
 * Created by yechaoa on 2021/2/5.
 * Describe :
 */
class TreeRepository : BaseRepository() {

    suspend fun getTree() = apiService().getTree().data()

}