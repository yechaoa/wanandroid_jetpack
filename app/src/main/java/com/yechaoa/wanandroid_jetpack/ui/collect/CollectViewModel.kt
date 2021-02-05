package com.yechaoa.wanandroid_jetpack.ui.collect

import androidx.lifecycle.MutableLiveData
import com.yechaoa.wanandroid_jetpack.base.BaseViewModel
import com.yechaoa.wanandroid_jetpack.data.bean.CollectDetail

class CollectViewModel : BaseViewModel() {

    private val repository by lazy { CollectRepository() }

    val collectList = MutableLiveData<MutableList<CollectDetail>>()

    fun getCollectList(page: Int) {
        launch(
            block = {
                collectList.value = repository.getCollectList(page).datas
            }
        )
    }

    val unCollectState = MutableLiveData<Boolean>()

    fun unCollectByCollect(id: Int, originId: Int) {
        launch(
            block = {
                val data = repository.unCollectByCollect(id, originId)
                unCollectState.value = 0 == data.code()
            }
        )
    }

}