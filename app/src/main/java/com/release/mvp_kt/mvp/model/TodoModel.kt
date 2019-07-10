package com.release.mvp_kt.mvp.model

import com.release.mvp_kt.base.BaseModel
import com.release.mvp_kt.http.RetrofitHelper
import com.release.mvp_kt.mvp.contract.TodoContract
import com.release.mvp_kt.mvp.model.bean.AllTodoResponseBody
import com.release.mvp_kt.mvp.model.bean.HttpResult
import com.release.mvp_kt.mvp.model.bean.TodoResponseBody
import io.reactivex.Observable
/**
 * @author Mr.release
 * @create 2019/7/1
 * @Describe
 */
class TodoModel : BaseModel(), TodoContract.Model {

    override fun getTodoList(type: Int): Observable<HttpResult<AllTodoResponseBody>> {
        return RetrofitHelper.service.getTodoList(type)
    }

    override fun getNoTodoList(page: Int, type: Int): Observable<HttpResult<TodoResponseBody>> {
        return RetrofitHelper.service.getNoTodoList(page, type)
    }

    override fun getDoneList(page: Int, type: Int): Observable<HttpResult<TodoResponseBody>> {
        return RetrofitHelper.service.getDoneList(page, type)
    }

    override fun deleteTodoById(id: Int): Observable<HttpResult<Any>> {
        return RetrofitHelper.service.deleteTodoById(id)
    }

    override fun updateTodoById(id: Int, status: Int): Observable<HttpResult<Any>> {
        return RetrofitHelper.service.updateTodoById(id, status)
    }

}