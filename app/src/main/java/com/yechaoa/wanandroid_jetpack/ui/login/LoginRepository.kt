package com.yechaoa.wanandroid_jetpack.ui.login

import com.yechaoa.wanandroid_jetpack.base.BaseRepository

/**
 * Created by yechaoa on 2021/2/4.
 * Describe :
 */
class LoginRepository : BaseRepository() {

    suspend fun login(username: String?, password: String?) = apiService().login(username, password)

}