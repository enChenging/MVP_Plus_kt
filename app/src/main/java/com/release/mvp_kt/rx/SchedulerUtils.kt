package com.release.mvp_kt.rx

import androidx.lifecycle.LifecycleOwner
import com.cxz.wanandroid.rx.scheduler.*
import com.uber.autodispose.AutoDispose
import com.uber.autodispose.AutoDisposeConverter
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider

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

    fun <T> bindLifecycle(lifecycleOwner: LifecycleOwner): AutoDisposeConverter<T> {
        return AutoDispose.autoDisposable(
            AndroidLifecycleScopeProvider.from(lifecycleOwner)
        )
    }
//    fun <T> bindLifecycle(lifecycleOwner: LifecycleOwner): AutoDisposeConverter<T> {
//        return AutoDispose.autoDisposable(
//            AndroidLifecycleScopeProvider.from(lifecycleOwner)
//        )
//    }
}