package com.yechaoa.wanandroid_jetpack.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yechaoa.wanandroid_jetpack.base.BaseViewModel

/**
 * Created by yechaoa on 2020/2/9.
 * Describe :
 */
class RegisterViewModel : BaseViewModel() {

    private val repository by lazy { RegisterRepository() }

    private val _registerState = MutableLiveData<Boolean>()
    val registerState: LiveData<Boolean> = _registerState

    fun register(username: String, password: String, repassword: String) {
        launch(
            block = {
                val loginData = repository.register(username, password, repassword)
                _registerState.value = 0 == loginData.code()
            }
        )
    }

}