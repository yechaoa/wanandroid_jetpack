package com.yechaoa.wanandroid_jetpack.util

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.yechaoa.wanandroid_jetpack.R
import java.math.RoundingMode
import java.text.DecimalFormat
import java.util.*

/**
 * 随机Color 避免纯色没有从255取值
 */
fun randomColor(): Int {
    Random().run {
        val red = nextInt(210)
        val green = nextInt(210)
        val blue = nextInt(210)
        return Color.rgb(red, green, blue)
    }
}

/**
 * 把搜索关键字变色，默认红色
 */
fun TextView.spanText(searchKey: String?, value: String?, color: Int = R.color.red) {
    if (!searchKey.isNullOrEmpty() && !value.isNullOrEmpty()
        && value.toLowerCase(Locale.getDefault())
            .contains(searchKey.toLowerCase(Locale.getDefault()))
    ) {
        searchKey.toLowerCase(Locale.getDefault()).apply {
            val builder = SpannableString(value)
            val list = value.toLowerCase(Locale.getDefault()).split(this)
            var recodeIndex = 0
            list.forEachIndexed { index, s ->
                if (index < list.size - 1) {
                    recodeIndex += s.length
                    builder.setSpan(
                        ForegroundColorSpan(ContextCompat.getColor(context, color)),
                        recodeIndex,
                        recodeIndex + length,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    recodeIndex += length
                }
            }
            text = builder
        }
    } else {
        text = value
    }
}

/**
 * 数据格式化
 */
fun numFormat(value: Double?): String {
    return if (value == null) "0.00" else DecimalFormat("0.00").apply {
        roundingMode = RoundingMode.HALF_UP
    }.format(value)
}