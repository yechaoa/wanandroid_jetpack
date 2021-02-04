package com.yechaoa.wanandroid_jetpack.ui.adapter

import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.yechaoa.wanandroid_jetpack.R
import com.yechaoa.wanandroid_kotlin.bean.DataX

/**
 * Created by yechaoa on 2021/2/4.
 * Describe :
 */
class ProjectChildAdapter(data: MutableList<DataX>) :
    BaseQuickAdapter<DataX, BaseViewHolder>(R.layout.item_project_child, data), LoadMoreModule {

    override fun convert(holder: BaseViewHolder, item: DataX) {
        Glide.with(context).load(item.envelopePic).into(holder.getView(R.id.iv_project_img))
        holder.setText(R.id.tv_project_title, item.title)
        holder.setText(R.id.tv_project_desc, item.desc)
        holder.setText(R.id.tv_project_date, item.niceDate)
        holder.setText(R.id.tv_project_author, item.author)
    }

}