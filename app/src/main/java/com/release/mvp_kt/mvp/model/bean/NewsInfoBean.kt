package com.release.mvp_kt.mvp.model.bean

/**
 * @author Mr.release
 * @create 2019/4/17
 * @Describe
 */

//data class HttpResult<T>(
//    val data: T
//) : BaseBean()

/************************************ 新闻信息 *******************************************/
data class NewsInfoBean(
    val boardid: String,
    val digest: String,
    val docid: String,
    val hasAD: Int,
    val isHasCover: Boolean,
    val hasHead: Int,
    val isHasIcon: Boolean,
    val hasImg: Int,
    val imgsrc: String,
    val lmodify: String,
    val order: Int,
    val postid: String,
    val priority: Int,
    val ptime: String?,
    val replyCount: Int,
    val source: String,
    val title: String,
    val votecount: Int,
    val imgextra: List<ImgextraBean>,
    val ads: List<AdData>?,
    val skipID: String,
    val alias: String,
    val skipType: String?,
    val tname: String,
    val specialID: String,
    val photosetID: String

)

data class ImgextraBean(
    val imgsrc: String
)


/**
 * 广告数据
 */
data class AdData(
    val title: String,
    val tag: String,
    val imgsrc: String,
    val subtitle: String,
    val url: String
)

/************************************ 新闻详情 *******************************************/
data class NewsDetailInfoBean(
    var body: String,
    val digest: String,
    val dkeys: String,
    val docid: String,
    val ec: String,
    val isHasNext: Boolean,
    val isPicnews: Boolean,
    val ptime: String,
    val replyBoard: String,
    val replyCount: Int,
    val shareLink: String,
    val source: String,
    val template: String,
    val threadAgainst: Int,
    val threadVote: Int,
    val tid: String,
    val title: String,
    val voicecomment: String,
    val img: List<ImgBean>,
    val link: List<*>,
    val spinfo: List<SpinfoBean>,
    val topiclist_news: List<TopiclistNewsBean>,
    val relative_sys: List<NewsItemInfoBean>
)


data class ImgBean(
    val alt: String,
    val pixel: String,
    val ref: String,
    val src: String
)

data class SpinfoBean(
    val ref: String,
    val spcontent: String,
    val sptype: String
)

data class TopiclistNewsBean(
    val alias: String,
    val cid: String,
    val ename: String,
    val isHasCover: Boolean,
    val subnum: String,
    val tid: String,
    val tname: String
)

/************************************ 整合专题和详情的新闻列表 *******************************************/
data class NewsItemInfoBean(
    val id: String,
    val docID: String,
    val type: String,
    val href: String,
    val postid: String,
    val votecount: Int,
    val replyCount: Int,
    val tag: String,
    val ltitle: String,
    val digest: String,
    val url: String,
    val ipadcomment: String,
    val docid: String,
    val title: String,
    val source: String,
    val lmodify: String,
    val imgsrc: String,
    val ptime: String,
    val skipType: String,
    val specialID: String,
    val photosetID: String
)

/************************************ 图集列表信息 *******************************************/
data class PhotoSetInfoBean(
    val autoid: String,
    val boardid: String,
    val clientadurl: String,
    val commenturl: String,
    val cover: String,
    val createdate: String,
    val creator: String,
    val datatime: String,
    val desc: String,
    val imgsum: String,
    val neteasecode: String,
    val postid: String,
    val reporter: String,
    val scover: String,
    val series: String,
    val setname: String,
    val settag: String,
    val source: String,
    val tcover: String,
    val url: String,
    val photos: List<PhotosBean>,
    val relatedids: List<*>

)


data class PhotosBean(
    val cimgurl: String,
    val imgtitle: String,
    val imgurl: String,
    val newsurl: String,
    val note: String,
    val photohtml: String,
    val photoid: String,
    val simgurl: String,
    val squareimgurl: String,
    val timgurl: String
)


/************************************ 新闻专题 *******************************************/
data class SpecialInfoBean(
    val banner: String,
    val del: Int,
    val digest: String,
    val ec: String,
    val imgsrc: String,
    val lmodify: String,
    val photoset: String,
    val ptime: String,
    val sdocid: String,
    val shownav: String,
    val sid: String,
    val sname: String,
    val tag: String,
    val type: String,
    val headpics: List<*>,
    val topics: List<TopicsBean>,
    val topicslatest: List<TopicslatestBean>,
    val topicspatch: List<*>,
    val topicsplus: List<*>,
    val webviews: List<*>
)

data class TopicsBean(
    val index: Int,
    val shortname: String,
    val tname: String,
    val type: String,
    val docs: List<NewsItemInfoBean>
)

data class TopicslatestBean(
    val index: Int,
    val shortname: String,
    val showformat: String,
    val timeformat: String,
    val tname: String,
    val type: String,
    val docs: List<DocsBeanX>
)


data class DocsBeanX(
    val digest: String,
    val docid: String,
    val imgsrc: String,
    val imgsum: Int,
    val ipadcomment: String,
    val lmodify: String,
    val occurtime: String,
    val postid: String,
    val ptime: String,
    val replyCount: Int,
    val tag: String,
    val title: String,
    val url: String,
    val votecount: Int,
    val important: String
)


/************************************ 新闻专题 *******************************************/
data class RecommendPageBean(
    val code: Int,
    val msg: String,
    val newslist: List<NewslistBean>
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