package com.yechaoa.wanandroid_jetpack.util

import android.animation.ObjectAnimator
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.yechaoa.wanandroid_jetpack.R

/**
 * ImageView旋转动画 180°
 */
fun startImageRotate(imageView: ImageView, toggle: Boolean) {
    val tarRotate: Float = if (toggle) 0f else 180f
    imageView.apply {
        ObjectAnimator.ofFloat(this, "rotation", rotation, tarRotate).let {
            it.duration = 300
            it.start()
        }
    }
}

/**
 * View展开收起动画
 */
fun View.topAnimation(show: Boolean) {
    //要显示且未显示 或 不要显示且显示中  满足二选一条件 即设置显示状态和动画
    if ((show && visibility == View.GONE) || (!show && visibility == View.VISIBLE)) {
        visibility = if (show) View.VISIBLE else View.GONE
        animation = AnimationUtils.loadAnimation(context, if (show) R.anim.top_in else R.anim.top_out)
    }
}

/**
 * View淡入淡出动画
 */
fun View.alphaAnimation(show: Boolean) {
    //要显示且未显示 或 不要显示且显示中  满足二选一条件 即设置显示状态和动画
    if ((show && visibility == View.GONE) || (!show && visibility == View.VISIBLE)) {
        visibility = if (show) View.VISIBLE else View.GONE
        animation = AnimationUtils.loadAnimation(context, if (show) R.anim.alpha_in else R.anim.alpha_out)
    }
}

/**
 * 设置view 背景 圆角
 */
fun View.setRoundRectBg(color: Int = Color.WHITE, radius: Float = 10f) {
    background = GradientDrawable().apply {
        setColor(color)
        cornerRadius = radius
    }
}

/**
 * 防止重复点击
 */
var lastClickTime = 0L
fun View.setOnclickNoRepeat(interval: Long = 500, onClick: (View) -> Unit) {
    this.setOnClickListener {
        val currentTime = System.currentTimeMillis()
        if (lastClickTime != 0L && (currentTime - lastClickTime < interval)) {
            return@setOnClickListener
        }
        lastClickTime = currentTime
        onClick.invoke(it)
    }
}