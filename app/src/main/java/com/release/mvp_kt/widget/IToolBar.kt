package com.release.mvp_kt.widget

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.release.mvp_kt.R


/**
 * @author Mr.release
 * @create 2019/4/25
 * @Describe
 */
class IToolBar @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    Toolbar(context, attrs, defStyleAttr) {

    private var toolBar: View? = null
    private var toolbarLayout: View? = null
    private var ivBack: ImageView? = null
    private var tvTitle: TextView? = null
    private var tvRight: TextView? = null

    init {
        initView(context)
    }


    private fun initView(context: Context) {
        val view = LayoutInflater.from(context).inflate(R.layout.tool_bar_back, this, true)
        toolBar = view.findViewById(R.id.toolBar)
        toolbarLayout = view.findViewById(R.id.toolBar_layout)
        ivBack = view.findViewById(R.id.iv_back)
        tvTitle = view.findViewById(R.id.tv_title)
        tvRight = view.findViewById(R.id.tv_right)

        setBackFinish()
    }

    fun setToolBarBackgroundColor(color: Int): IToolBar {
        toolBar?.background = resources.getDrawable(color)
        return this
    }

    fun setToolLayoutBackgroundColor(color: Int): IToolBar {
        toolbarLayout?.setBackgroundColor(resources.getColor(color))
        return this
    }

    fun setBackGone(): IToolBar {
        ivBack?.visibility = View.GONE
        return this
    }

    fun setBackDrawable(drawable: Drawable): IToolBar {
        ivBack?.setImageDrawable(drawable)
        return this
    }

    fun setBackDrawable(color: Int): IToolBar {
        ivBack?.setImageDrawable(resources.getDrawable(color))
        return this
    }

    fun setRight(right: String, clickListener: View.OnClickListener): IToolBar {
        tvRight?.visibility = View.VISIBLE
        tvRight?.text = right
        tvRight?.setOnClickListener(clickListener)
        return this
    }

    fun setRight(right: Int, clickListener: View.OnClickListener): IToolBar {
        tvRight?.visibility = View.VISIBLE
        tvRight?.setText(right)
        tvRight?.setOnClickListener(clickListener)
        return this
    }

    fun setRightGone(Visible: Int): IToolBar {
        tvRight?.visibility = Visible
        return this
    }


    fun setRightTextColor(color: Int): IToolBar {
        tvRight?.setTextColor(resources.getColor(color))
        return this
    }

    fun setRightSize(textSize: Int): IToolBar {
        tvRight?.textSize = textSize.toFloat()
        return this
    }

    fun setBackFinish(): IToolBar {
        ivBack?.setOnClickListener { (context as Activity).finish() }
        return this
    }


    fun setTitleText(title: String?): IToolBar {
        tvTitle?.text = title
        return this
    }

    fun setTitleText(textId: Int): IToolBar {
        tvTitle?.setText(textId)
        return this
    }

    fun setTitleColor(color: Int): IToolBar {
        tvTitle?.setTextColor(resources.getColor(color))
        return this
    }

    fun setTitleSize(textSize: Int): IToolBar {
        tvTitle?.textSize = textSize.toFloat()
        return this
    }

}
