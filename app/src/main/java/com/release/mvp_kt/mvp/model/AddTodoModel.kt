package com.release.mvp_kt.mvp.model

import com.release.mvp_kt.base.BaseModel
import com.release.mvp_kt.http.RetrofitHelper
import com.release.mvp_kt.mvp.contract.AddTodoContract
import com.release.mvp_kt.mvp.model.bean.HttpResult
import io.reactivex.Observable

/**
 * @author Mr.release
 * @create 2019/7/1
 * @Describe
 */
class AddTodoModel : BaseModel(), AddTodoContract.Model {

    override fun addTodo(map: MutableMap<String, Any>): Observable<HttpResult<Any>> {
        return RetrofitHelper.service.addTodo(map)
    }

    override fun updateTodo(id: Int, map: MutableMap<String, Any>): Observable<HttpResult<Any>> {
        return RetrofitHelper.service.updateTodo(id, map)
    }

}