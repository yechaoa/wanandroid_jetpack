package com.yechaoa.wanandroid_jetpack.ui.web

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.KeyEvent
import android.widget.LinearLayout
import com.just.agentweb.AgentWeb
import com.yechaoa.wanandroid_jetpack.base.BaseActivity
import com.yechaoa.wanandroid_jetpack.databinding.ActivityWebBinding
import com.yechaoa.yutilskt.LogUtil

class WebActivity : BaseActivity<ActivityWebBinding>() {

    companion object {
        const val WEB_URL: String = "web_url"
        const val WEB_TITLE: String = "web_title"
        const val WEB_TYPE: String = "type"

        const val TYPE_PATH = 1001//只有路径
        const val TYPE_URL = 1002//全链接

        fun launch(context: Context, url: String, title: String = "", type: Int = TYPE_PATH) {
            val intent = Intent(context, WebActivity::class.java)
            intent.putExtra(WEB_URL, url)
            intent.putExtra(WEB_TITLE, title)
            intent.putExtra(WEB_TYPE, type)
            context.startActivity(intent)
        }
    }

    private lateinit var mAgentWeb: AgentWeb

    override fun getViewBinding(): ActivityWebBinding {
        return ActivityWebBinding.inflate(layoutInflater)
    }

    override fun init() {
        setTitle()
        initAgentWeb()
        setListener()
    }

    private fun setTitle() {
        var title = intent.getStringExtra(WEB_TITLE)
        title?.let {
            if (it.length > 12) {
                title = it.substring(0, 12) + "..."
            }
            mBinding.tvTitle.text = title
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initAgentWeb() {
        mAgentWeb = AgentWeb.with(this)
            .setAgentWebParent(mBinding.webContent, LinearLayout.LayoutParams(-1, -1))
            .useDefaultIndicator()
            .createAgentWeb()
            .ready()
            .go(getLoadUrl())

        LogUtil.i(getLoadUrl().toString())

        val webView = mAgentWeb.webCreator.webView
        webView.settings.run {
            //获取手势焦点
            webView.requestFocusFromTouch()
            //支持JS
            javaScriptEnabled = true
            //是否支持缩放
            setSupportZoom(true)
            builtInZoomControls = true
            //是否显示缩放按钮
            displayZoomControls = true
            //自适应屏幕
            useWideViewPort = false
            loadWithOverviewMode = false
        }
    }

    private fun getLoadUrl(): String? {
        return intent.getStringExtra(WEB_URL)
    }

    private fun setListener() {
        mBinding.toolbar.setNavigationOnClickListener {
            super.onBackPressed()
        }
    }

    /**
     * 事件处理
     */
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return mAgentWeb.handleKeyEvent(keyCode, event) || super.onKeyDown(keyCode, event)
    }

    /**
     * 跟随 Activity Or Fragment 生命周期 ， 释放 CPU 更省电 。
     */
    override fun onPause() {
        mAgentWeb.webLifeCycle.onPause()
        super.onPause()
    }

    override fun onResume() {
        mAgentWeb.webLifeCycle.onResume()
        super.onResume()
    }

    override fun onDestroy() {
        mAgentWeb.webLifeCycle.onDestroy()
        super.onDestroy()
    }

}