package com.yechaoa.wanandroid_jetpack.ui.main.navi

import androidx.lifecycle.MutableLiveData
import com.yechaoa.wanandroid_jetpack.base.BaseViewModel
import com.yechaoa.wanandroid_jetpack.data.bean.Navi

class NaviViewModel : BaseViewModel() {

    private val repository by lazy { NaviRepository() }

    val naviList = MutableLiveData<MutableList<Navi>>()

    fun getNavi() {
        launch(
            block = {
                naviList.value = repository.getNavi()
            }
        )
    }
}