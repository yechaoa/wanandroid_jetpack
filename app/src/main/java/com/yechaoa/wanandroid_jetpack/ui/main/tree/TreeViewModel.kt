package com.yechaoa.wanandroid_jetpack.ui.main.tree

import androidx.lifecycle.MutableLiveData
import com.yechaoa.wanandroid_jetpack.base.BaseViewModel
import com.yechaoa.wanandroid_jetpack.data.bean.Tree

class TreeViewModel : BaseViewModel() {

    private val repository by lazy { TreeRepository() }

    val treeList = MutableLiveData<MutableList<Tree>>()

    fun getTree() {
        launch(
            block = {
                treeList.value = repository.getTree()
            }
        )
    }

}