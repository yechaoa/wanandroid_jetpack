package com.yechaoa.wanandroid_jetpack.ui.register

import android.view.View
import com.yechaoa.wanandroid_jetpack.base.BaseVmActivity
import com.yechaoa.wanandroid_jetpack.databinding.ActivityRegisterBinding
import com.yechaoa.wanandroid_jetpack.util.setOnclickNoRepeat
import com.yechaoa.yutilskt.ToastUtil
import com.yechaoa.yutilskt.YUtils

class RegisterActivity : BaseVmActivity<ActivityRegisterBinding, RegisterViewModel>() {

    override fun viewModelClass(): Class<RegisterViewModel> {
        return RegisterViewModel::class.java
    }

    override fun getViewBinding(): ActivityRegisterBinding {
        return ActivityRegisterBinding.inflate(layoutInflater)
    }

    override fun initView() {
        mBinding.btnRegister.setOnclickNoRepeat {
            attemptRegister()
        }
    }

    private fun attemptRegister() {
        YUtils.closeSoftKeyboard()

        mBinding.etUsername.error = null
        mBinding.etPassword.error = null
        mBinding.etRepassword.error = null

        val username = mBinding.etUsername.text.toString().trim()
        val password = mBinding.etPassword.text.toString().trim()
        val repassword = mBinding.etRepassword.text.toString().trim()

        var cancel = false
        var focusView: View? = null

        if (repassword.isEmpty()) {
            mBinding.etRepassword.error = "确认密码不能为空"
            focusView = mBinding.etRepassword
            cancel = true
        }

        if (password.isEmpty()) {
            mBinding.etPassword.error = "密码不能为空"
            focusView = mBinding.etPassword
            cancel = true
        }

        if (password != repassword) {
            mBinding.etPassword.error = "两次密码不一致"
            focusView = mBinding.etPassword
            cancel = true
        }

        if (username.isEmpty()) {
            mBinding.etUsername.error = "账号不能为空"
            focusView = mBinding.etUsername
            cancel = true
        }

        if (cancel) focusView?.requestFocus()
        else doRegister(username, password, repassword)

    }

    private fun doRegister(username: String, password: String, repassword: String) {
        YUtils.showLoading(this, "加载中")
        mViewModel.register(username, password, repassword)
    }

    override fun observe() {
        super.observe()
        mViewModel.registerState.observe(this, {
            YUtils.hideLoading()
            if (it) {
                ToastUtil.show("注册成功，请登录")
                finish()
            } else {
                ToastUtil.show("注册失败")
            }
        })
    }

    override fun setListener() {
        super.setListener()
        mBinding.toolbar.setNavigationOnClickListener {
            super.onBackPressed()
        }
    }
}
