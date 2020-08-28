package com.release.mvp_kt.mvp.model

data class RecommendPageBean(
    val code: Int,
    val msg: String,
    val newslist: MutableList<NewslistBean>
) {
    override fun toString(): String {
        return "RecommendPageBean(code=$code, msg='$msg', newslist=$newslist)"
    }
}


class NewslistBean(
    val ctime: String,
    val title: String,
    val description: String,
    val picUrl: String,
    val url: String
) {
    override fun toString(): String {
        return "NewslistBean(ctime='$ctime', title='$title', description='$description', picUrl='$picUrl', url='$url')"
    }
}