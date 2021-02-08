package com.yechaoa.wanandroid_jetpack.data.bean

/**
 * Created by yechaoa on 2021/2/5.
 * Describe :
 */
data class Banner(
    val desc: String,
    val id: Int,
    val imagePath: String,
    val isVisible: Int,
    val order: Int,
    val title: String,
    val type: Int,
    val url: String
)