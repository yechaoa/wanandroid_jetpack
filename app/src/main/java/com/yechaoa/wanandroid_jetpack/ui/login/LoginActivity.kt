package com.yechaoa.wanandroid_jetpack.ui.login

import android.content.Intent
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.core.content.ContextCompat
import com.gyf.immersionbar.ImmersionBar
import com.yechaoa.wanandroid_jetpack.R
import com.yechaoa.wanandroid_jetpack.base.BaseVmActivity
import com.yechaoa.wanandroid_jetpack.common.MyConfig
import com.yechaoa.wanandroid_jetpack.databinding.ActivityLoginBinding
import com.yechaoa.wanandroid_jetpack.ui.main.MainActivity
import com.yechaoa.wanandroid_jetpack.ui.register.RegisterActivity
import com.yechaoa.wanandroid_jetpack.util.setOnclickNoRepeat
import com.yechaoa.yutilskt.ActivityUtil
import com.yechaoa.yutilskt.SpUtil
import com.yechaoa.yutilskt.ToastUtil
import com.yechaoa.yutilskt.YUtils
import java.util.*

class LoginActivity : BaseVmActivity<ActivityLoginBinding, LoginViewModel>() {

    override fun getViewBinding(): ActivityLoginBinding {
        return ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun viewModelClass(): Class<LoginViewModel> = LoginViewModel::class.java

    override fun initView() {
        ImmersionBar.with(this).fitsSystemWindows(true).transparentStatusBar().statusBarDarkFont(true).init()
        setText()
    }

    override fun setListener() {
        mBinding.tvRegister.setOnclickNoRepeat {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        mBinding.btnLogin.setOnclickNoRepeat {
            if (!mBinding.cbServiceAgreement.isChecked) {
                ToastUtil.show("同意服务协议与隐私政策后才能登录")
                return@setOnclickNoRepeat
            }
            attemptLogin()
        }
    }

    private fun attemptLogin() {
        YUtils.closeSoftKeyboard()

        mBinding.etUsername.error = null
        mBinding.etPassword.error = null

        val username = mBinding.etUsername.text.toString().trim()
        val password = mBinding.etPassword.text.toString().trim()

        var cancel = false
        var focusView: View? = null

        if (password.isEmpty()) {
            mBinding.etPassword.error = "密码不能为空"
            focusView = mBinding.etPassword
            cancel = true
        }

        if (username.isEmpty()) {
            mBinding.etUsername.error = "账号不能为空"
            focusView = mBinding.etPassword
            cancel = true
        }

        if (cancel) focusView?.requestFocus()
        else doLogin(username, password)
    }

    private fun doLogin(username: String, password: String) {
        YUtils.showLoading(this, "加载中")
        mViewModel.login(username, password)
    }

    override fun observe() {
        super.observe()
        mViewModel.loginState.observe(this, {
            YUtils.hideLoading()
            SpUtil.setBoolean(MyConfig.IS_LOGIN, it)
            if (it) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                ToastUtil.show("登录失败")
            }
        })
    }

    private fun setText() {
        val spanBuilder = SpannableStringBuilder("同意")
        val color = ContextCompat.getColor(applicationContext, R.color.colorPrimary)

        var span = SpannableString("服务协议")
        span.setSpan(object : ClickableSpan() {
            override fun onClick(widget: View) {
                ToastUtil.show("服务协议")
            }
        }, 0, span.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        span.setSpan(ForegroundColorSpan(color), 0, span.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        spanBuilder.append(span)

        spanBuilder.append("与")

        span = SpannableString("隐私政策")
        span.setSpan(object : ClickableSpan() {
            override fun onClick(widget: View) {
                ToastUtil.show("隐私政策")
            }
        }, 0, span.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        span.setSpan(ForegroundColorSpan(color), 0, span.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        spanBuilder.append(span)

        mBinding.tvServiceAgreement.movementMethod = LinkMovementMethod.getInstance()
        mBinding.tvServiceAgreement.text = spanBuilder
        //设置高亮颜色透明，因为点击会变色
        mBinding.tvServiceAgreement.highlightColor = ContextCompat.getColor(applicationContext, R.color.transparent)
    }

    override fun onBackPressed() {
        ActivityUtil.closeAllActivity()
    }

}