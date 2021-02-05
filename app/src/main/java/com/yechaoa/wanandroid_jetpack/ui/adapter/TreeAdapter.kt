package com.yechaoa.wanandroid_jetpack.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.yechaoa.wanandroid_jetpack.R
import com.yechaoa.wanandroid_jetpack.data.bean.Tree
import com.yechaoa.wanandroid_jetpack.util.startImageRotate
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import com.zhy.view.flowlayout.TagFlowLayout
import java.util.*

/**
 * Created by yechaoa on 2021/2/4.
 * Describe :
 */
class TreeAdapter : BaseQuickAdapter<Tree, BaseViewHolder>(R.layout.item_tree), TagFlowLayout.OnTagClickListener {

    override fun convert(holder: BaseViewHolder, item: Tree) {
        holder.setText(R.id.tv_tree_title, item.name)

        val flowLayout = holder.getView<TagFlowLayout>(R.id.flow_layout)

        //根据状态处理显示结果
        if (item.isShow) {
            flowLayout.visibility = View.VISIBLE
            holder.setImageResource(R.id.iv_toggle, R.mipmap.ic_up)
        } else {
            flowLayout.visibility = View.GONE
            holder.setImageResource(R.id.iv_toggle, R.mipmap.ic_down)
        }

        flowLayout.adapter = object : TagAdapter<Tree.Children>(item.children) {
            override fun getView(parent: FlowLayout, position: Int, s: Tree.Children): View {
                val tvTag = LayoutInflater.from(context).inflate(R.layout.item_tree_item, flowLayout, false) as TextView
                tvTag.text = s.name
                tvTag.setTextColor(randomColor())
                return tvTag
            }
        }
        //设置点击事件
        flowLayout.setOnTagClickListener(this)
    }

    /**
     * 定义接口,比原来接口多一个parentPosition参数，父view的position
     */
    interface OnItemTagClickListener {
        fun onItemTagClick(view: View?, position: Int, parent: FlowLayout?): Boolean
    }

    private var mOnItemTagClickListener: OnItemTagClickListener? = null

    /**
     * 给adapter添加事件方法
     */
    fun setOnItemTagClickListener(listener: OnItemTagClickListener?) {
        this.mOnItemTagClickListener = listener
    }

    /**
     * 在事件中传入自定义接口
     */
    override fun onTagClick(view: View?, position: Int, parent: FlowLayout?): Boolean {
        mOnItemTagClickListener?.onItemTagClick(view, position, parent)
        return true
    }

    fun randomColor(): Int {
        Random().run {
            val red = nextInt(210)
            val green = nextInt(210)
            val blue = nextInt(210)
            return Color.rgb(red, green, blue)
        }
    }
}