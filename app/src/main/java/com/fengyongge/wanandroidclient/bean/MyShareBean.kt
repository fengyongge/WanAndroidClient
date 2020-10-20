package com.fengyongge.wanandroidclient.bean

data class MyShareBean(
    val coinInfo: CoinInfo,
    val shareArticles: ShareArticles
)

data class CoinInfo(
    val coinCount: Int,
    val level: Int,
    val rank: String,
    val userId: Int,
    val username: String
)

data class ShareArticles(
    val curPage: Int,
    val datas: List<MyShareData>,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int
)

data class MyShareData(
    val apkLink: String,
    val author: String,
    val chapterId: Int,
    val chapterName: String,
    val collect: Boolean,
    val courseId: Int,
    val desc: String,
    val envelopePic: String,
    val fresh: Boolean,
    val id: Int,
    val link: String,
    val niceDate: String,
    val origin: String,
    val prefix: String,
    val projectLink: String,
    val publishTime: Long,
    val superChapterId: Int,
    val superChapterName: String,
    val tags: List<MyShareTag>,
    val title: String,
    val type: Int,
    val userId: Int,
    val visible: Int,
    val zan: Int
)

data class MyShareTag(
    val name: String,
    val url: String
)