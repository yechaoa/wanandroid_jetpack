package com.yechaoa.wanandroid_jetpack.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.yechaoa.wanandroid_jetpack.data.bean.History

/**
 * Created by yechaoa on 2020/2/4.
 * Describe : https://developer.android.google.cn/training/data-storage/room
 * entities 数组，对应此数据库中的所有表
 * version 数据库版本号
 */
@Database(entities = [History::class], version = 1)
abstract class HistoryDatabase : RoomDatabase() {

    abstract fun historyDao(): HistoryDao

    companion object {
        private const val DATABASE_NAME = "history.db"
        private lateinit var mPersonDatabase: HistoryDatabase

        //注意：如果您的应用在单个进程中运行，在实例化 AppDatabase 对象时应遵循单例设计模式。
        //每个 RoomDatabase 实例的成本相当高，而您几乎不需要在单个进程中访问多个实例
        fun getInstance(context: Context): HistoryDatabase {
            if (!this::mPersonDatabase.isInitialized) {
                //创建的数据库的实例
                mPersonDatabase = Room.databaseBuilder(
                    context.applicationContext,
                    HistoryDatabase::class.java,
                    DATABASE_NAME
                ).build()
            }
            return mPersonDatabase
        }
    }

}