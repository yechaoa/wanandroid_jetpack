package com.yechaoa.wanandroid_jetpack.data.http

import com.yechaoa.wanandroid_jetpack.base.BaseBean
import com.yechaoa.wanandroid_jetpack.data.bean.*
import com.yechaoa.wanandroid_kotlin.bean.*
import retrofit2.http.*

/**
 * Created by yechaoa on 2020/2/4.
 * Describe :
 */
interface Api {

    companion object {
        const val isTest = false
        const val isDev = false
        const val BASE_URL = "https://www.wanandroid.com/"
    }

    //-----------------------【登录注册】----------------------

    //登录
    @FormUrlEncoded
    @POST("user/login")
    suspend fun login(
        @Field("username") username: String?,
        @Field("password") password: String?
    ): BaseBean<UserBean>

    //注册
    @FormUrlEncoded
    @POST("user/register")
    suspend fun register(
        @Field("username") username: String?,
        @Field("password") password: String?,
        @Field("repassword") repassword: String?
    ): BaseBean<UserBean>


    //-----------------------【首页相关】----------------------

    //首页文章列表
    @GET("article/list/{page}/json")
    suspend fun getArticleList(@Path("page") page: Int): BaseBean<Article>

    //首页banner
    @GET("banner/json")
    suspend fun getBanner(): BaseBean<MutableList<Banner>>


    //-----------------------【 体系 】----------------------

    //体系数据
    @GET("tree/json")
    suspend fun getTree(): BaseBean<MutableList<Tree>>

    //知识体系下的文章
    @GET("article/list/{page}/json?")
    suspend fun getTreeChild(
        @Path("page") page: Int,
        @Query("cid") cid: Int
    ): BaseBean<Article>

    //-----------------------【 导航 】----------------------

    //导航数据
    @GET("navi/json")
    suspend fun getNavi(): BaseBean<MutableList<Navi>>


    //-----------------------【 项目 】----------------------

    //项目分类
    @GET("project/tree/json")
    suspend fun getProject(): BaseBean<MutableList<Project>>

    //项目列表数据
    @GET("project/list/{page}/json?")
    suspend fun getProjectChild(
        @Path("page") page: Int,
        @Query("cid") cid: Int
    ): BaseBean<ProjectChild>


    //-----------------------【 搜索 】----------------------

    //搜索
    @FormUrlEncoded
    @POST("article/query/{page}/json?")
    suspend fun getSearchList(
        @Path("page") page: Int,
        @Field("k") k: String
    ): BaseBean<Article>

    //搜索热词
    @GET("hotkey/json")
    suspend fun getHotkey(): BaseBean<MutableList<Hotkey>>

    //-----------------------【 收藏 】----------------------

    //收藏文章列表
    @GET("lg/collect/list/{page}/json?")
    suspend fun getCollectList(@Path("page") page: Int): BaseBean<Collect>

    //收藏站内文章
    @POST("lg/collect/{id}/json")
    suspend fun collect(@Path("id") id: Int): BaseBean<String>

    //取消收藏（文章列表）
    @POST("lg/uncollect_originId/{id}/json")
    suspend fun unCollect(@Path("id") id: Int): BaseBean<String>

    //取消收藏（我的收藏页面）
    @FormUrlEncoded
    @POST("lg/uncollect/{id}/json")
    suspend fun unCollect1(
        @Path("id") id: Int,
        @Field("originId") originId: Int
    ): BaseBean<String>

}