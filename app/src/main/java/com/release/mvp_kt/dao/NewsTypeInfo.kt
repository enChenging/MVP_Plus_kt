package com.release.mvp_kt.dao

import org.litepal.crud.LitePalSupport

/**
 * @author Mr.release
 * @create 2019/3/29
 * @Describe
 */

data class  NewsTypeInfo (
    val id: Long,
    val name: String,
    val typeId: String
): LitePalSupport()
