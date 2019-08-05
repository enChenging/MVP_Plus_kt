package com.release.mvp_kt.widget

import android.animation.ValueAnimator
import android.annotation.TargetApi
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Build
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import com.release.mvp_kt.R


/**
 * Created by Corleone on 2018/7/6.
 */
class NoticeDialog(
    ctx: Context, private val canNotCancel: Boolean, title: String, content: String,
    private val mListener: View.OnClickListener, isCanceledOnTouchOutside: Boolean
) : Dialog(ctx, R.style.MyDialog) {
    private val mTv_content: TextView
    private val mTv_title: TextView
    private val mTv_cancel: TextView
    private val mTv_ok: TextView
    private val mIv_close: ImageView

    init {

        setContentView(R.layout.dialog_welcom_permission)

        mTv_title = findViewById(R.id.tv_title)
        mTv_content = findViewById(R.id.tv_content)
        mTv_cancel = findViewById(R.id.tv_cancel)
        mTv_ok = findViewById(R.id.tv_ok)
        mIv_close = findViewById(R.id.iv_close)


        mTv_title.text = title + ""
        mTv_content.text = content + ""

        mTv_cancel.setOnClickListener(mListener)
        mTv_ok.setOnClickListener(mListener)
        mIv_close.setOnClickListener(mListener)

        val windowManager = (ctx as Activity).windowManager
        val display = windowManager.defaultDisplay
        val lp = window!!.attributes
        lp.width = display.width * 4 / 5
        window!!.attributes = lp
        setCanceledOnTouchOutside(isCanceledOnTouchOutside)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (canNotCancel) {
                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    companion object {

        private var noticeDialog: NoticeDialog? = null

        fun show(context: Context, content: String, listener: View.OnClickListener) {
            show(context, "提醒", content, false, true, listener)
        }

        fun show(context: Context, content: String, listener: View.OnClickListener, isCanceledOnTouchOutside: Boolean) {
            show(context, "提醒", content, false, isCanceledOnTouchOutside, listener)
        }

        fun show(context: Context, title: String, content: String, listener: View.OnClickListener) {
            show(context, title, content, false, true, listener)
        }


        fun show(
            context: Context,
            title: String,
            content: String,
            isCanceledOnTouchOutside: Boolean,
            listener: View.OnClickListener
        ) {
            show(context, title, content, false, isCanceledOnTouchOutside, listener)
        }


        fun show(
            context: Context,
            title: String,
            content: String,
            isCancel: Boolean,
            isCanceledOnTouchOutside: Boolean,
            listener: View.OnClickListener
        ) {
            if (context is Activity) {
                if (context.isFinishing) {
                    return
                }
            }
            if (noticeDialog != null && noticeDialog!!.isShowing) {
                return
            }
            noticeDialog = NoticeDialog(context, isCancel, title, content, listener, isCanceledOnTouchOutside)
            noticeDialog!!.show()
        }


        fun dismissDialog() {
            if (noticeDialog != null && noticeDialog!!.isShowing && !noticeDialog!!.context.isRestricted) {
                noticeDialog!!.dismiss()
                noticeDialog = null
            }
        }

        fun setWindowAlpa(context: Activity, isopen: Boolean) {
            if (Build.VERSION.SDK_INT < 11) {
                return
            }
            val window = context.window
            val lp = window.attributes
            window.setFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND, WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            val animator: ValueAnimator
            if (isopen) {
                animator = ValueAnimator.ofFloat(1.0f, 0.5f)
            } else {
                animator = ValueAnimator.ofFloat(0.5f, 1.0f)
            }
            animator.duration = 400
            animator.addUpdateListener { animation ->
                val alpha = animation.animatedValue as Float
                lp.alpha = alpha
                window.attributes = lp
            }
            animator.start()
        }
    }

}

