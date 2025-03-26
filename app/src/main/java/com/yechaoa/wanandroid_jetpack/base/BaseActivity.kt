package com.yechaoa.wanandroid_jetpack.base

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.gyf.immersionbar.ImmersionBar
import com.yechaoa.wanandroid_jetpack.R
import com.yechaoa.yutilskt.ToastUtil

abstract class BaseActivity<VB : ViewBinding>(private val inflate: (LayoutInflater) -> VB) : AppCompatActivity() {

    protected open lateinit var mBinding: VB

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = inflate(layoutInflater)
        setContentView(mBinding.root)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT//竖屏

        // 初始化沉浸式 模拟器状态栏白色 see：https://github.com/gyf-dev/ImmersionBar/issues/543
        ImmersionBar.with(this)
            .fitsSystemWindows(true)
            .statusBarColor(R.color.colorPrimary)
            .navigationBarColor(R.color.transparent)
            .init()

        initialize()
    }

    /**
     * 命名与子类要区分，否则会先调用BaseActivity中同名方法，再调用BaseVmActivity中同名方法，可能会出现调用顺序导致的错误
     */
    open fun initialize() {}

    override fun onDestroy() {
        super.onDestroy()
        ToastUtil.release()
    }
}