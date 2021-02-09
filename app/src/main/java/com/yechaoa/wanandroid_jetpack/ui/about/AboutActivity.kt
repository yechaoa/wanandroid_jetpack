package com.yechaoa.wanandroid_jetpack.ui.about

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.AssetManager
import android.graphics.Paint
import android.graphics.Typeface
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
    override fun initialize() {
        super.initialize()

        mBinding.toolbar.title = "${getString(R.string.wanandroid)}  V${YUtils.getVersionName()}"
        mBinding.toolbar.setNavigationOnClickListener {
            super.onBackPressed()
        }

        mContentAboutBinding = mBinding.contentAbout

        //添加下划线
        mContentAboutBinding.tvAuthor.paint.flags = Paint.UNDERLINE_TEXT_FLAG
        mContentAboutBinding.tvAuthor.setOnClickListener {
            val intent = Intent(this, DetailActivity::class.java).apply {
                putExtra(DetailActivity.WEB_URL, getString(R.string.csdn))
                putExtra(DetailActivity.WEB_TITLE, getString(R.string.author))
            }
            startActivity(intent)
        }

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
        }

        setTypeface()
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
        mContentAboutBinding.tvAuthor.typeface = typeface
        mContentAboutBinding.tvGithub.typeface = typeface
        mContentAboutBinding.tvApi.typeface = typeface
        mContentAboutBinding.tvLibrary.typeface = typeface
    }

}
