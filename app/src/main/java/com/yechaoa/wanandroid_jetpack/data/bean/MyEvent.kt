package com.yechaoa.wanandroid_jetpack.data.bean

/**
 * Created by yechaoa on 2021/2/4.
 * Describe : 总线数据
 */
import java.io.Serializable

class MyEvent(
        val key: String,
        val value: String,
        val data: Any? = null
) : Serializable {
    override fun toString(): String {
        return "FrEvent(key=$key, value=$value)"
    }
}