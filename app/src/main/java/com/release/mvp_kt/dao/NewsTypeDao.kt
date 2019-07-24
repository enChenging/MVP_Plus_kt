package com.release.mvp_kt.dao

import android.content.Context
import com.google.gson.Gson
import com.release.mvp_kt.utils.CommonUtil
import org.litepal.LitePal

/**
 * @author Mr.release
 * @create 2019/3/29
 * @Describe
 */
object NewsTypeDao {


    /**
     * 更新本地数据，如果数据库新闻列表栏目为 0 则添加头 3 个栏目
     *
     * @param context
     * @param daoSession
     */
    fun updateLocalData(context: Context) {

        val readData = CommonUtil.readData(context, "NewsChannel")
        val allChannels = Gson().fromJson(readData, Array<NewsTypeInfo>::class.java).toMutableList()
        val beanDao = LitePal.findAll(NewsTypeInfo::class.java)
        if (beanDao.size == 0) {
            //            beanDao.insertInTx(sAllChannels.subList(0, 3));
            for (item in allChannels) {
                item.save()
            }
        }
    }
}
