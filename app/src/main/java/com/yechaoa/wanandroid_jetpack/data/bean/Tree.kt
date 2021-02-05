package com.yechaoa.wanandroid_jetpack.data.bean

import java.io.Serializable

/**
 * Created by yechaoa on 2021/2/5.
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
) : Serializable {
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
}


