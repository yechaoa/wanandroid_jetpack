package com.yechaoa.wanandroid_kotlin.bean

/**
 * Created by yechao on 2020/1/20/020.
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