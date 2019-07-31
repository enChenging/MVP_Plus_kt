package com.release.mvp_kt.widget

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.annotation.IntDef
import com.github.ybq.android.spinkit.SpinKitView
import com.github.ybq.android.spinkit.sprite.Sprite
import com.release.mvp_kt.R
import com.release.mvp_kt.base.BaseMvpActivity
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

/**
 * @author Mr.release
 * @create 2019/3/22
 * @Describe
 */
class EmptyLayout @JvmOverloads constructor(private val mContext: Context, attrs: AttributeSet? = null) :
    FrameLayout(mContext, attrs) {
    private var mOnRetryListener: OnRetryListener? = null
    private var mEmptyStatus = STATUS_LOADING
    private var mBgColor: Int = 0

    private lateinit var mTvEmptyMessage: TextView
    private lateinit var mRlEmptyContainer: View
    private lateinit var mEmptyLoading: SpinKitView
    private lateinit var mEmptyLayout: FrameLayout

    /**
     * 获取状态
     *
     * @return 状态
     */
    /**
     * 设置状态
     *
     * @param emptyStatus
     */
    var emptyStatus: Int
        get() = mEmptyStatus
        set(@EmptyStatus emptyStatus) {
            mEmptyStatus = emptyStatus
            _switchEmptyView()
        }

    init {
        init(attrs)
    }

    /**
     * 初始化
     */
    private fun init(attrs: AttributeSet?) {
        val a = mContext.obtainStyledAttributes(attrs, R.styleable.EmptyLayout)
        try {
            mBgColor = a.getColor(R.styleable.EmptyLayout_background_color, Color.WHITE)
        } finally {
            a.recycle()
        }
        View.inflate(mContext, R.layout.layout_empty_loading, this)

        mTvEmptyMessage = findViewById(R.id.tv_net_error)
        mRlEmptyContainer = findViewById(R.id.rl_empty_container)
        mEmptyLoading = findViewById(R.id.empty_loading)
        mEmptyLayout = findViewById(R.id.fl_empty_layout)

        mEmptyLayout.setBackgroundColor(mBgColor)
        _switchEmptyView()

        mTvEmptyMessage.setOnClickListener { v ->
            if (mOnRetryListener != null) {
                mOnRetryListener!!.onRetry()
            }
        }
    }

    /**
     * 隐藏视图
     */
    fun hide() {
        mEmptyStatus = STATUS_HIDE
        _switchEmptyView()
    }

    /**
     * 设置异常消息
     *
     * @param msg 显示消息
     */
    fun setEmptyMessage(msg: String) {
        mTvEmptyMessage.text = msg
    }

    fun hideErrorIcon() {
        mTvEmptyMessage.setCompoundDrawables(null, null, null, null)
    }

    fun setLoadingIcon(d: Sprite) {
        mEmptyLoading.setIndeterminateDrawable(d)
    }

    /**
     * 切换视图
     */
    private fun _switchEmptyView() {
        when (mEmptyStatus) {
            STATUS_LOADING -> {
                visibility = View.VISIBLE
                mRlEmptyContainer.visibility = View.GONE
                mEmptyLoading.visibility = View.VISIBLE
            }
            STATUS_NO_DATA, STATUS_NO_NET -> {
                visibility = View.VISIBLE
                mEmptyLoading.visibility = View.GONE
                mRlEmptyContainer.visibility = View.VISIBLE
            }
            STATUS_HIDE -> visibility = View.GONE
        }
    }

    /**
     * 设置重试监听器
     *
     * @param retryListener 监听器
     */
    fun setRetryListener(retryListener: OnRetryListener) {
        this.mOnRetryListener = retryListener
    }

    /**
     * 点击重试监听器
     */
    interface OnRetryListener {
        fun onRetry()
    }


    @Retention(RetentionPolicy.SOURCE)
    @IntDef(STATUS_LOADING, STATUS_NO_NET, STATUS_NO_DATA)
    annotation class EmptyStatus

    companion object {

        const val STATUS_HIDE = 1001
        const val STATUS_LOADING = 1
        const val STATUS_NO_NET = 2
        const val STATUS_NO_DATA = 3
    }
}