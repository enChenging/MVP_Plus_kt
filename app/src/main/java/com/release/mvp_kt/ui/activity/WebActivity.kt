package com.release.mvp_kt.ui.activity

import android.net.http.SslError
import android.os.Build
import android.view.KeyEvent
import android.webkit.SslErrorHandler
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout
import com.just.agentweb.AgentWeb
import com.just.agentweb.NestedScrollAgentWebView
import com.release.mvp_kt.R
import com.release.mvp_kt.base.BaseMvpSwipeBackActivity
import com.release.mvp_kt.constant.Constant
import com.release.mvp_kt.ext.getAgentWeb
import com.release.mvp_kt.mvp.contract.WebContracnt
import com.release.mvp_kt.mvp.presenter.WebPresenter
import kotlinx.android.synthetic.main.activity_web.*
import kotlinx.android.synthetic.main.toolbar.*
import com.just.agentweb.WebChromeClient
import com.just.agentweb.WebViewClient

/**
 * @author Mr.release
 * @create 2019/6/25
 * @Describe
 */
class WebActivity : BaseMvpSwipeBackActivity<WebContracnt.View, WebContracnt.Presenter>(), WebContracnt.View {

    private var agentWeb: AgentWeb? = null
    private lateinit var shareTitle: String
    private lateinit var shareUrl: String
    private val mWebView: NestedScrollAgentWebView by lazy {
        NestedScrollAgentWebView(this)
    }

    override fun createPresenter(): WebContracnt.Presenter = WebPresenter()

    override fun initLayoutID(): Int = R.layout.activity_web


    override fun initView() {
        super.initView()

        initToolBar()
        toolbar.apply {
            tv_title.apply {
                text = getString(R.string.loading)
                postDelayed({
                    tv_title.isSelected = true
                }, 2000)
            }
        }

        intent.extras?.let {
            shareTitle = it.getString(Constant.CONTENT_TITLE_KEY, "")
            shareUrl = it.getString(Constant.CONTENT_URL_KEY, "")
        }

        initWebView()
    }

    private fun initWebView() {
        val layoutParams = CoordinatorLayout.LayoutParams(-1, -1)
        layoutParams.behavior = AppBarLayout.ScrollingViewBehavior()

        agentWeb = shareUrl.getAgentWeb(
            this,
            cl_main,
            layoutParams,
            mWebView,
            webChromeClient,
            webViewClient
        )

        agentWeb?.webCreator?.webView?.let {
            it.settings.domStorageEnabled = true
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                it.settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            }
        }
    }

    /**
     * webViewClient
     */
    private val webViewClient = object : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
            return super.shouldOverrideUrlLoading(view, request)
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
        }

        override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
            handler?.proceed()
        }
    }


    /**
     * webChromeClient
     */
    private val webChromeClient = object : WebChromeClient() {
        override fun onProgressChanged(view: WebView?, newProgress: Int) {

        }

        override fun onReceivedTitle(view: WebView?, title: String?) {
            super.onReceivedTitle(view, title)
            title.let {
                tv_title.text = it
            }
        }
    }

    override fun onBackPressed() {
        agentWeb?.let {
            if (!it.back()) {
                super.onBackPressed()
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if (agentWeb?.handleKeyEvent(keyCode, event)!!) {
            true
        } else {
            finish()
            super.onKeyDown(keyCode, event)
        }
    }

    override fun onResume() {
        agentWeb?.webLifeCycle?.onResume()
        super.onResume()
    }

    override fun onPause() {
        agentWeb?.webLifeCycle?.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        agentWeb?.webLifeCycle?.onDestroy()
        super.onDestroy()
    }
}