package com.yechaoa.wanandroid_jetpack.ui.register

import com.yechaoa.wanandroid_jetpack.base.BaseRepository

/**
 * Created by yechaoa on 2021/2/9.
 * Describe :
 */
class RegisterRepository : BaseRepository() {

    suspend fun register(username: String, password: String, repassword: String) =
        apiService().register(username, password, repassword)

}