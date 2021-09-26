package com.yechaoa.wanandroid_jetpack

/**
 * test custom lint
 */
class Test {
    // We have a custom lint check bundled with :library
    // that this module depends on. The lint check looks
    // for mentions of "lint", which should trigger an
    // error
    val s = "lint"
    fun lint() { }

    override fun toString(): String {
        return super.toString()
    }

    fun a(){}
}
