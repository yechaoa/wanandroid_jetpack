package com.yechaoa.wanandroid_jetpack.ui.about

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.AssetManager
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.view.MenuItem
import android.view.View
import com.yechaoa.wanandroid_jetpack.R
import com.yechaoa.wanandroid_jetpack.base.BaseActivity
import com.yechaoa.wanandroid_jetpack.databinding.ActivityAboutBinding
import com.yechaoa.wanandroid_jetpack.databinding.ContentAboutBinding
import com.yechaoa.wanandroid_jetpack.ui.detail.DetailActivity
import com.yechaoa.yutilskt.ShareUtil
import com.yechaoa.yutilskt.YUtils

class AboutActivity : BaseActivity<ActivityAboutBinding>() {

    private lateinit var mContentAboutBinding: ContentAboutBinding

    override fun getViewBinding(): ActivityAboutBinding {
        return ActivityAboutBinding.inflate(layoutInflater)
    }

    @SuppressLint("SetTextI18n")
    override fun init() {
        super.init()

        mBinding.toolbar.setNavigationOnClickListener {
            super.onBackPressed()
        }

        mContentAboutBinding = mBinding.contentAbout
        mContentAboutBinding.tvAppInfo.text = "玩安卓  V${YUtils.getVersionName()}"

        //添加下划线
        mContentAboutBinding.tvGithub.paint.flags = Paint.UNDERLINE_TEXT_FLAG
        mContentAboutBinding.tvGithub.setOnClickListener {
            val intent = Intent(this, DetailActivity::class.java).apply {
                putExtra(DetailActivity.WEB_URL, getString(R.string.github))
                putExtra(DetailActivity.WEB_TITLE, getString(R.string.app_name))
            }
            startActivity(intent)
        }

        mBinding.fab.setOnClickListener {
            ShareUtil.shareText(getString(R.string.wanandroid), getString(R.string.github))
            shareProject()
        }

        setTypeface()
    }

    /**
     * 调用系统的分享功能
     */
    private fun shareProject() {
        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, "玩安卓")
            putExtra(Intent.EXTRA_TEXT, "https://github.com/yechaoa/wanandroid_kotlin")
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivity(Intent.createChooser(intent, "玩安卓"))
    }

    /**
     * 设置字体
     */
    private fun setTypeface() {
        //获取AssetManager
        val assets = assets as AssetManager
        //根据路径得到字体
        val typeface = Typeface.createFromAsset(assets, "fonts/mononoki-Regular.ttf")
        //设置给TextView
        mContentAboutBinding.tvAppInfo.typeface = typeface
        mContentAboutBinding.tvGithub.typeface = typeface
        mContentAboutBinding.tvApi.typeface = typeface
        mContentAboutBinding.tvLibrary.typeface = typeface
    }

    /**
     * 返回键
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}
