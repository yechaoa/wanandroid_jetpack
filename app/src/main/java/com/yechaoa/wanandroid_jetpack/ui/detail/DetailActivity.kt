package com.yechaoa.wanandroid_jetpack.ui.detail

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.Color
import android.view.Gravity
import android.view.KeyEvent
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import com.just.agentweb.AgentWeb
import com.just.agentweb.WebViewClient
import com.yechaoa.wanandroid_jetpack.base.BaseActivity
import com.yechaoa.wanandroid_jetpack.databinding.ActivityDetailBinding
import com.yechaoa.yutilskt.LogUtil

class DetailActivity : BaseActivity<ActivityDetailBinding>() {

    companion object {
        const val WEB_URL: String = "web_url"
        const val WEB_TITLE: String = "web_title"
    }

    private lateinit var mAgentWeb: AgentWeb

    override fun getViewBinding(): ActivityDetailBinding {
        return ActivityDetailBinding.inflate(layoutInflater)
    }

    override fun init() {
        super.init()
        mBinding.tvTitle.text = intent.getStringExtra(WEB_TITLE)
        setListener()
        initAgentWeb()
    }

    private fun setListener() {
        mBinding.toolbar.setNavigationOnClickListener {
            super.onBackPressed()
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initAgentWeb() {
        mAgentWeb = AgentWeb.with(this)
            .setAgentWebParent(mBinding.webContent, LinearLayout.LayoutParams(-1, -1))
            .useDefaultIndicator()
            .setWebViewClient(mWebViewClient)
            .createAgentWeb()
            .ready()
            .go(getLoadUrl())

        val webView = mAgentWeb.webCreator.webView
        //获取手势焦点
        webView.requestFocusFromTouch()
        webView.settings.apply {
            //支持JS
            javaScriptEnabled = true
            //是否支持缩放
            setSupportZoom(true)
            builtInZoomControls = true
            //是否显示缩放按钮
            displayZoomControls = false
            //自适应屏幕
            useWideViewPort = true
            loadWithOverviewMode = true
        }
        addBGChild(mAgentWeb.webCreator.webParentLayout as FrameLayout) // 得到 AgentWeb 最底层的控件
    }

    private fun addBGChild(frameLayout: FrameLayout) {
        val title = "技术由 AgentWeb 提供"
        val mTextView = TextView(frameLayout.context)
        mTextView.text = title
        mTextView.textSize = 16f
        mTextView.setTextColor(Color.parseColor("#727779"))
        frameLayout.setBackgroundColor(Color.parseColor("#272b2d"))
        val mFlp = FrameLayout.LayoutParams(-2, -2)
        mFlp.gravity = Gravity.CENTER_HORIZONTAL
        val scale: Float = frameLayout.context.resources.displayMetrics.density
        mFlp.topMargin = (15 * scale + 0.5f).toInt()
        frameLayout.addView(mTextView, 0, mFlp)
    }

    private fun getLoadUrl(): String? {
        return intent.getStringExtra(WEB_URL)
    }

    private val mWebViewClient: WebViewClient = object : WebViewClient() {

        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
            //获取url
            val uri = request!!.url
            LogUtil.i(uri.toString())
            /**
             * 一个URL通常由以下几个部分构成：协议、域名、端口、路径和URL地址参数。
             * https://baike.baidu.com/item/android/60243
             * uri.scheme 协议 https
             * uri.authority 域名 baike.baidu.com
             * uri.path 路径 item/android/60243
             * uri.queryParameterNames 参数 null
             * uri.getQueryParameter("id")
             */
            return super.shouldOverrideUrlLoading(view, request)
        }


        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            LogUtil.i("url---$url")
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            LogUtil.i("url---$url")
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
