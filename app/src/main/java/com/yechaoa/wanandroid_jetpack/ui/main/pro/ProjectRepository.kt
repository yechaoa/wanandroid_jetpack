package com.yechaoa.wanandroid_jetpack.ui.main.pro

import com.yechaoa.wanandroid_jetpack.base.BaseRepository

/**
 * Created by yechaoa on 2021/2/8.
 * Describe :
 */
class ProjectRepository : BaseRepository() {

    suspend fun getProject() = apiService().getProject().data()

    suspend fun getProjectChild(page: Int, cid: Int) = apiService().getProjectChild(page, cid).data()

}