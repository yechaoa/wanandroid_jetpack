package com.yechaoa.wanandroid_jetpack.ui.main.navi

import com.yechaoa.wanandroid_jetpack.base.BaseRepository

/**
 * Created by yechaoa on 2021/2/8.
 * Describe :
 */
class NaviRepository : BaseRepository() {

    suspend fun getNavi() = apiService().getNavi().data()

}