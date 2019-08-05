package com.release.mvp_kt.ui.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.view.View
import com.release.mvp_kt.MainActivity
import com.release.mvp_kt.R
import com.release.mvp_kt.base.BaseActivity
import com.release.mvp_kt.utils.StatusBarUtil
import com.release.mvp_kt.widget.InstallUtil
import com.release.mvp_kt.widget.NoticeDialog
import com.tbruyelle.rxpermissions2.RxPermissions
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.uber.autodispose.android.lifecycle.autoDisposable
import com.uber.autodispose.autoDisposable
import kotlinx.android.synthetic.main.activity_splash.*

/**
 * @author Mr.release
 * @create 2019/8/5
 * @Describe
 */
class SplashActivity : BaseActivity() {

    override fun initLayoutID(): Int = R.layout.activity_splash

    private val scopeProvider by lazy {
        AndroidLifecycleScopeProvider.from(this)
    }

    private var hasPermission: Boolean = false
    private var isNever: Boolean = false

    override fun initView() {
        super.initView()
        if (Build.VERSION.SDK_INT >= 23)
            requestCameraPermissions()
        else
            jump()
    }

    override fun initListener() {
        btn_permission.run {
            setOnClickListener {
                if (Build.VERSION.SDK_INT >= 23)
                    requestCameraPermissions()
            }
        }
    }

    private fun jump() {
        Intent(this, MainActivity::class.java).run {
            startActivity(this)
            overridePendingTransition(R.anim.slide_right_entry, R.anim.hold)
        }
    }

    override fun onResume() {
        super.onResume()
        if (isNever) {
            if (Build.VERSION.SDK_INT >= 23)
                requestCameraPermissions()
        }
    }

    /**
     * 请求权限
     */
    private fun requestCameraPermissions() {
        RxPermissions(this).requestEachCombined(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
            .autoDisposable(scopeProvider)
            .subscribe { permission ->
                when {
                    permission.granted -> {
                        hasPermission = true
                        jump()
                    }
                    permission.shouldShowRequestPermissionRationale ->
                        showNotice(resources.getString(R.string.rationale_wr))
                    else -> {
                        isNever = true
                        showNotice(resources.getString(R.string.rationale_ask_again))
                    }
                }
            }

    }

    private fun showNotice(content: String) {
        NoticeDialog.show(this, content, View.OnClickListener {
            when (it.id) {
                R.id.iv_close, R.id.tv_cancel -> btn_permission.visibility = View.VISIBLE
                R.id.tv_ok -> if (isNever)
                    InstallUtil.startAppSettings(this)
            }
            NoticeDialog.dismissDialog()
        }, false)
    }

    override fun initThemeColor() {
        StatusBarUtil.setTranslucent(this, 0)
    }
}