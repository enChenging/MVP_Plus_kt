package com.release.mvp_kt.utils

import com.release.mvp_kt.mvp.model.bean.NewsInfoBean
import io.reactivex.annotations.NonNull

/**
 * Created by Rukey7 on 2016/8/22.
 * 工具类
 */
object NewsUtils {


    // 新闻列表头部
    private const val HAS_HEAD = 1
    // 新闻ID长度
    private const val NEWS_ID_LENGTH = 16
    // 新闻ID前缀，eg：BV9KHEMS0001124J
    private const val NEWS_ID_PREFIX = "BV"
    // 新闻ID后缀，eg：http://news.163.com/17/0116/16/CATR1P580001875N.html
    private const val NEWS_ID_SUFFIX = ".html"

    private const val NEWS_ITEM_SPECIAL = "special"
    private const val NEWS_ITEM_PHOTO_SET = "photoset"


    /**
     * 判断是否为广告
     *
     * @param newsBean
     * @return
     */
    fun isAbNews(@NonNull newsBean: NewsInfoBean): Boolean {
        return newsBean.hasHead == HAS_HEAD && newsBean.ads.size > 1
    }

    /**
     * 从超链接中取出新闻ID
     *
     * @param url url
     * @return 新闻ID
     */
    fun clipNewsIdFromUrl(url: String): String? {
        var newsId: String? = null
        var index = url.indexOf(NEWS_ID_PREFIX)
        if (index != -1) {
            newsId = url.substring(index, index + NEWS_ID_LENGTH)
        } else if (url.endsWith(NEWS_ID_SUFFIX)) {
            index = url.length - NEWS_ID_SUFFIX.length - NEWS_ID_LENGTH
            newsId = url.substring(index, index + NEWS_ID_LENGTH)
        }
        return newsId
    }

    /**
     * 判断新闻类型
     *
     * @param skipType
     * @return
     */
    fun isNewsSpecial(skipType: String): Boolean {
        return NEWS_ITEM_SPECIAL == skipType
    }

    fun isNewsPhotoSet(skipType: String): Boolean {
        return NEWS_ITEM_PHOTO_SET == skipType
    }

}
