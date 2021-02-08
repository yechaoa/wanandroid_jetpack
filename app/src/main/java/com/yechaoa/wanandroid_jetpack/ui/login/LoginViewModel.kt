package com.yechaoa.wanandroid_jetpack.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yechaoa.wanandroid_jetpack.base.BaseViewModel
import com.yechaoa.wanandroid_jetpack.data.http.ApiException

/**
 * Created by yechaoa on 2020/2/4.
 * Describe :
 */
class LoginViewModel : BaseViewModel() {

    private val loginRepository by lazy { LoginRepository() }

    private val _loginState = MutableLiveData<Boolean>()
    val loginState: LiveData<Boolean> = _loginState

    fun login(username: String?, password: String?) {
        val job = launch(
            block = {
                val loginData = loginRepository.login(username, password)
                _loginState.value = 0 == loginData.code()
            },
            error = {
                //(其实在BaseViewModel处理就行了，这里作为演示)
                if (it is ApiException) {
                    // -1001 代表登录失效，需要重新登录
                    if (-1001 == it.code) {
                        _loginState.value = false
                    }
                }
            },
            cancel = {

            },
            false
        )

        //取消操作 即返回的launch对象
        //cancelJob(job)
    }

}