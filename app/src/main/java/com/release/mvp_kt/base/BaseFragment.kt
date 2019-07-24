package com.release.mvp_kt.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.classic.common.MultipleStatusView
import com.release.mvp_kt.constant.Constant
import com.release.mvp_kt.utils.Sp
import com.release.mvp_kt.widget.EmptyLayout
import kotlinx.android.synthetic.main.layout_empty.*
import org.greenrobot.eventbus.EventBus

/**
 * @author Mr.release
 * @create 2019/7/10
 * @Describe
 */
abstract class BaseFragment : androidx.fragment.app.Fragment() {

    /**
     * 视图是否加载完毕
     */
    private var isViewPrepare = false
    /**
     * 数据是否加载过了
     */
    private var hasLoadData = false
    /**
     * check login
     */
    protected var isLogin: Boolean by Sp(Constant.LOGIN_KEY, false)

    protected var mLayoutStatusView: MultipleStatusView? = null

    abstract fun initLayoutID(): Int

    open fun initData() {}

    open fun initView(view: View) {}

    open fun initListener() {}

    open fun startNet() {}

    /**
     * 是否使用 EventBus
     */
    open fun useEventBus(): Boolean = false

    open fun doReConnected() {
        startNet()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(initLayoutID(), null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (useEventBus()) {
            EventBus.getDefault().register(this)
        }
        isViewPrepare = true
        initData()
        initView(view)
        initListener()
        lazyLoadDataIfPrepared()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser)
            lazyLoadDataIfPrepared()
    }

    private fun lazyLoadDataIfPrepared() {
        if (userVisibleHint && isViewPrepare && !hasLoadData) {
            startNet()
            hasLoadData = true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (useEventBus()) {
            EventBus.getDefault().unregister(this)
        }
    }

}