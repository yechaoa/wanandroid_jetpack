package com.yechaoa.wanandroid_jetpack.ui.main.tree.child

import androidx.lifecycle.MutableLiveData
import com.yechaoa.wanandroid_jetpack.base.BaseViewModel
import com.yechaoa.wanandroid_jetpack.data.bean.Article
import com.yechaoa.wanandroid_jetpack.data.bean.Tree

class TreeChildViewModel : BaseViewModel() {

    private val repository by lazy { TreeChildRepository() }

    val articleList = MutableLiveData<MutableList<Article.ArticleDetail>>()

    fun getTreeChild(page: Int, cid: Int) {
        launch(
            block = {
                articleList.value = repository.getTreeChild(page, cid).datas
            }
        )
    }

    val collectState = MutableLiveData<Boolean>()

    fun collect(id: Int) {
        launch(
            block = {
                collectState.value = 0 == repository.collect(id).code()
            }
        )
    }

    val unCollectState = MutableLiveData<Boolean>()

    fun unCollect(id: Int) {
        launch(
            block = {
                unCollectState.value = 0 == repository.unCollectByArticle(id).code()
            }
        )
    }

}