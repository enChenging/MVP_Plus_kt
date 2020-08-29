@file:Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package com.release.mvp_kt.ext

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import com.google.android.material.snackbar.Snackbar
import androidx.core.content.ContextCompat
import android.view.ViewGroup
import com.just.agentweb.WebChromeClient
import com.just.agentweb.WebViewClient
import android.webkit.WebView
import android.widget.TextView
import com.just.agentweb.AgentWeb
import com.just.agentweb.DefaultWebClient
import com.release.mvp_kt.R
import com.release.mvp_kt.widget.CustomToast
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author Mr.release
 * @create 2019/6/26
 * @Describe
 */

fun androidx.fragment.app.Fragment.showToast(content: String) {
    CustomToast(this.activity, content).show()
}

fun Context.showToast(content: String) {
    CustomToast(this, content).show()
}

fun androidx.fragment.app.Fragment.showSnackMsg(msg:String){
    val snackbar = com.google.android.material.snackbar.Snackbar.make(this.activity!!.window.decorView, msg, com.google.android.material.snackbar.Snackbar.LENGTH_SHORT)
    val view = snackbar.view
    view.findViewById<TextView>(R.id.snackbar_text).setTextColor(ContextCompat.getColor(this.activity!!, R.color.white))
    snackbar.show()
}

fun Activity.showSnackMsg(msg: String) {
    val snackbar = Snackbar.make(this.window.decorView, msg, Snackbar.LENGTH_SHORT)
    val view = snackbar.view
    view.findViewById<TextView>(R.id.snackbar_text).setTextColor(ContextCompat.getColor(this, R.color.white))
    snackbar.show()
}

fun String.getAgentWeb(
    activity: Activity,
    webContent: ViewGroup,
    layoutParams: ViewGroup.LayoutParams,
    webView: WebView,
    webChromeClient: WebChromeClient?,
    webViewClient: WebViewClient
) = AgentWeb.with(activity)//传入Activity or Fragment
    .setAgentWebParent(webContent, 1, layoutParams)//传入AgentWeb 的父控件
    .useDefaultIndicator()
    .setWebView(webView)
    .setWebChromeClient(webChromeClient)
    .setWebViewClient(webViewClient)
    .setMainFrameErrorView(R.layout.agentweb_error_page, -1)
    .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)//打开其他应用时，弹窗咨询用户是否前往其他应用
    .createAgentWeb()
    .ready()
    .go(this)

/**
 * 格式化当前日期
 */
@SuppressLint("SimpleDateFormat")
fun formatCurrentDate(): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd")
    return sdf.format(Date())
}

/**
 * String 转 Calendar
 */
@SuppressLint("SimpleDateFormat")
fun String.stringToCalendar(): Calendar {
    val sdf = SimpleDateFormat("yyyy-MM-dd")
    val date = sdf.parse(this)
    val calendar = Calendar.getInstance()
    calendar.time = date
    return calendar
}