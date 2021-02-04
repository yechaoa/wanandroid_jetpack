package com.yechaoa.wanandroid_jetpack.data.bean

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by yechaoa on 2021/2/4.
 * Describe : 搜索历史
 */
@Entity(tableName = "t_history")
data class History(

    /**
     * @PrimaryKey主键 唯一 ，autoGenerate = true 自增
     * @ColumnInfo 列 ，typeAffinity 字段类型
     * @Ignore 忽略
     */

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id", typeAffinity = ColumnInfo.INTEGER)
    val id: Int? = null,

    @ColumnInfo(name = "name", typeAffinity = ColumnInfo.TEXT)
    val name: String?,

    @ColumnInfo(name = "insert_time", typeAffinity = ColumnInfo.TEXT)
    val insertTime: String?,

    @ColumnInfo(name = "type", typeAffinity = ColumnInfo.INTEGER)
    val type: Int = 1//类型 1文章 2微课
)
