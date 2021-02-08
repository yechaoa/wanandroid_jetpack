package com.yechaoa.wanandroid_jetpack.ui.main.pro

import androidx.lifecycle.MutableLiveData
import com.yechaoa.wanandroid_jetpack.base.BaseViewModel
import com.yechaoa.wanandroid_jetpack.data.bean.Article
import com.yechaoa.wanandroid_jetpack.data.bean.Project

class ProjectViewModel : BaseViewModel() {

    private val repository by lazy { ProjectRepository() }

    val proList = MutableLiveData<MutableList<Project>>()

    fun getProject() {
        launch(
            block = {
                proList.value = repository.getProject()
            }
        )
    }

    val childList = MutableLiveData<MutableList<Article.ArticleDetail>>()

    fun getProjectChild(page: Int, cid: Int) {
        launch(
            block = {
                childList.value = repository.getProjectChild(page, cid).datas
            }
        )
    }
}