package com.yechaoa.wanandroid_jetpack.data.bean

/**
 * Created by yechao on 2020/1/17/017.
 * Describe :
 */

data class Article(
    val curPage: Int,
    val datas: MutableList<ArticleDetail>,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int
) {
    data class ArticleDetail(
        val apkLink: String,
        val audit: Int,
        val author: String,
        val chapterId: Int,
        val chapterName: String,
        var collect: Boolean,
        val courseId: Int,
        val desc: String,
        val envelopePic: String,
        val fresh: Boolean,
        val id: Int,
        val link: String,
        val niceDate: String,
        val niceShareDate: String,
        val origin: String,
        val prefix: String,
        val projectLink: String,
        val publishTime: Long,
        val selfVisible: Int,
        val shareDate: Long,
        val shareUser: String,
        val superChapterId: Int,
        val superChapterName: String,
        val tags: List<Tag>,
        val title: String,
        val type: Int,
        val userId: Int,
        val visible: Int,
        val zan: Int
    ) {
        data class Tag(
            val name: String,
            val url: String
        )
    }
}



