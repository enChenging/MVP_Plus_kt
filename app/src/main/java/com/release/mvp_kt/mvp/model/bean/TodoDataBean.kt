package com.release.mvp_kt.mvp.model.bean

import com.chad.library.adapter.base.entity.SectionEntity

/**
 * @author Mr.release
 * @create 2019/7/1
 * @Describe
 */
class TodoDataBean : SectionEntity<TodoBean> {

    constructor(isHeader: Boolean, headerName: String) : super(isHeader, headerName)

    constructor(todoBean: TodoBean) : super(todoBean)

}