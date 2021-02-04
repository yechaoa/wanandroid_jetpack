package com.yechaoa.wanandroid_jetpack.common

/**
 * Created by yechaoa on 2020/2/4.
 * Describe :
 */
object MyConfig {

    const val IS_LOGIN="isLogin"

    const val COOKIE="cookie"

    /**
     * 用户认证状态
     */
    object UserAuditStatus {
        const val unauthorized = 0//未认证
        const val through = 1//认证通过
        const val notThrough = 2//认证未通过
    }

}