package com.cxz.wanandroid.rx.scheduler

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
* @author Mr.release
* @create 2019/7/10
* @Describe
*/
class ComputationMainScheduler<T> : BaseScheduler<T>(Schedulers.computation(), AndroidSchedulers.mainThread())
