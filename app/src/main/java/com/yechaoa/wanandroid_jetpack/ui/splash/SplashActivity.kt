package com.yechaoa.wanandroid_jetpack.ui.splash

import android.content.Intent
import android.os.CountDownTimer
import com.yechaoa.wanandroid_jetpack.base.BaseActivity
import com.yechaoa.wanandroid_jetpack.common.MyConfig
import com.yechaoa.wanandroid_jetpack.databinding.ActivitySplashBinding
import com.yechaoa.wanandroid_jetpack.ui.MainActivity
import com.yechaoa.wanandroid_jetpack.ui.login.LoginActivity
import com.yechaoa.wanandroid_jetpack.util.setOnclickNoRepeat
import com.yechaoa.yutilskt.SpUtil

class SplashActivity : BaseActivity<ActivitySplashBinding>() {

    private lateinit var timer: CountDownTimer

    override fun getViewBinding(): ActivitySplashBinding {
        return ActivitySplashBinding.inflate(layoutInflater)
    }

    override fun init() {
        super.init()
        setListener()
    }

    private fun setListener() {
        mBinding.tvSkip.setOnclickNoRepeat {
            if (this::timer.isInitialized) timer.cancel()
            checkLogin()
        }
    }

    override fun onResume() {
        super.onResume()
        startCountDown()
    }

    private fun startCountDown() {
        timer = object : CountDownTimer(1 * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {}

            override fun onFinish() {
                timer.cancel()
                checkLogin()
            }
        }
        timer.start()
    }

    private fun checkLogin() {
        return if (!SpUtil.getBoolean(MyConfig.IS_LOGIN)) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        } else {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

}