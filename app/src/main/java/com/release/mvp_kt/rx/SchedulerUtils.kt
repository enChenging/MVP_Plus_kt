package com.release.mvp_kt.rx

import com.cxz.wanandroid.rx.scheduler.*

/**
 * @author Mr.release
 * @create 2019/7/10
 * @Describe
 */
object SchedulerUtils {

    fun <T> ioToMain(): IoMainScheduler<T> {
        return IoMainScheduler()
    }

    fun <T> computationToMain(): ComputationMainScheduler<T> {
        return ComputationMainScheduler()
    }

    fun <T> newThreadToMain(): NewThreadMainScheduler<T> {
        return NewThreadMainScheduler()
    }

    fun <T> singleToMain(): SingleMainScheduler<T> {
        return SingleMainScheduler()
    }

    fun <T> trampolineToMain(): TrampolineMainScheduler<T> {
        return TrampolineMainScheduler()
    }
}