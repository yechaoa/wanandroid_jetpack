package com.yechaoa.wanandroid_jetpack.data.bean

/**
 * Created by yechaoa on 2021/2/8.
 * Describe :
 */
data class Navi(
    val articles: MutableList<Article.ArticleDetail>,
    val cid: Int,
    val name: String
)