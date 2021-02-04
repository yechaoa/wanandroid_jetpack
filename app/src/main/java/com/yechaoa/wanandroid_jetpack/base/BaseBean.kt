package com.yechaoa.wanandroid_jetpack.base

import com.yechaoa.wanandroid_jetpack.data.http.ApiException

/**
 * Created by yechaoa on 2020/2/4.
 * Describe :
 */
class BaseBean<T>(private val errorCode: Int, private val data: T, private val message: String?) {

    fun code(): Int {
        if (errorCode == 0) {
            return errorCode
        } else {
            throw ApiException(errorCode, message ?: "")
        }
    }

    fun data(): T {
        if (errorCode == 0) {
            return data
        } else {
            throw ApiException(errorCode, message ?: "")
        }
    }

}