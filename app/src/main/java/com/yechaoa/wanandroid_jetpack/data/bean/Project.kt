package com.yechaoa.wanandroid_jetpack.data.bean

/**
 * Created by yechaoa on 2021/2/8.
 * Describe :
 */
data class Project(
    val children: List<Any>,
    val courseId: Int,
    val id: Int,
    val name: String,
    val order: Int,
    val parentChapterId: Int,
    val userControlSetTop: Boolean,
    val visible: Int
)