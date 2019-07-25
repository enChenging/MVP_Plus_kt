package com.release.mvp_kt.constant

/**
 * @author Mr.release
 * @create 2019/6/26
 * @Describe
 */

object Constant {

    const val NEWS_HOST = "http://c.3g.163.com/"

    const val RECOMMEND_HOST = "http://api.tianapi.com/"

    const val COMMON_UA_STR = "OhMyBiliBili Android Client/2.1 (100332338@qq.com)"

    const val BUGLY_ID = "bd6527b403"

    const val HEAD_LINE_NEWS = "T1348647909107"

    const val LOGIN_KEY = "login"
    const val TOKEN_KEY = "token"
    const val HAS_NETWORK_KEY = "has_network"

    /**
     * url key
     */
    const val CONTENT_URL_KEY = "url"
    /**
     * title key
     */
    const val CONTENT_TITLE_KEY = "title"
    /**
     * share key
     */
    const val CONTENT_SHARE_TYPE = "text/plain"
    /**
     * 网络请求的状态码，用来更新界面
     */
    const val STATE_SUCCESS = 0
    const val STATE_ERROR = 1
    const val STATE_LOADING = 2
    const val STATE_EMPTY = 3

    /**
     * config
     */
    const val CURRENT_TIME = "currentTime"
    const val PAGE = 20

    const val NEWS_TYPE_KEY = "NewsTypeKey"
    const val NEWS_TYPE_TITLE = "NewsTypeTitle"
    const val NEWS_ID_KEY = "NewsIdKey"
    const val SPECIAL_KEY = "SpecialKey"
    const val PHOTO_SET_KEY = "PhotoSetKey"
    const val VIDEO_ID_KEY = "VideoIdKey"
}
