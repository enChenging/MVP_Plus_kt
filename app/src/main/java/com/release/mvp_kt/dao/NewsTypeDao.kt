package com.release.mvp_kt.dao

import android.content.Context
import com.alibaba.fastjson.JSONArray
import com.release.mvp_kt.utils.CommonUtil
import org.litepal.LitePal
import org.litepal.extension.findAll

/**
 * @author Mr.release
 * @create 2019/3/29
 * @Describe
 */
object NewsTypeDao {

    /**
     * 获取所有栏目
     *
     * @return
     */
    var allChannels: MutableList<NewsTypeInfo>? = null
        private set

    /**
     * 更新本地数据，如果数据库新闻列表栏目为 0 则添加头 3 个栏目
     *
     * @param context
     * @param daoSession
     */
    fun updateLocalData(context: Context) {
        allChannels = JSONArray.parseArray(CommonUtil.readData(context, "NewsChannel"), NewsTypeInfo::class.java)
//        val beanDao = daoSession.newsTypeInfoDao
        val beanDao = LitePal.findAll(NewsTypeInfo::class.java)
        if (beanDao.size == 0) {
            //            beanDao.insertInTx(sAllChannels.subList(0, 3));
            for (item in allChannels!!) {
                item.save()
            }
        }
    }
}
