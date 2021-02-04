package com.yechaoa.wanandroid_kotlin.bean

import java.io.Serializable

/**
 * Created by yechao on 2020/1/19/019.
 * Describe :
 */

data class Tree(
    val children: ArrayList<Children>,
    var isShow: Boolean,
    val courseId: Int,
    val id: Int,
    val name: String,
    val order: Int,
    val parentChapterId: Int,
    val userControlSetTop: Boolean,
    val visible: Int
) : Serializable


data class Children(
    val children: ArrayList<Any>,
    val courseId: Int,
    val id: Int,
    val name: String,
    val order: Int,
    val parentChapterId: Int,
    val userControlSetTop: Boolean,
    val visible: Int
) : Serializable