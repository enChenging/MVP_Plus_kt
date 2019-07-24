package com.release.mvp_kt

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDexApplication
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import com.release.mvp_kt.constant.Constant
import com.release.mvp_kt.dao.NewsTypeDao
import com.release.mvp_kt.ext.showToast
import com.release.mvp_kt.utils.CommonUtil
import com.release.mvp_kt.utils.CrashHandler
import com.release.mvp_kt.utils.DisplayManager
import com.tencent.bugly.Bugly
import com.tencent.bugly.beta.Beta
import com.tencent.bugly.beta.upgrade.UpgradeStateListener
import com.tencent.bugly.crashreport.CrashReport
import org.litepal.LitePal
import kotlin.properties.Delegates

/**
 * @author Mr.release
 * @create 2019/7/10
 * @Describe
 */
class App : MultiDexApplication() {


    companion object {
        var context: Context by Delegates.notNull()
        lateinit var instance: Application
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        context = applicationContext
        initConfig()
        DisplayManager.init(this)
        initLitePal()
        initBugly()
    }

    /**
     * 初始化 Bugly
     */
    private fun initBugly() {
        if (BuildConfig.DEBUG) {
            return
        }
        // 获取当前包名
        val packageName = applicationContext.packageName
        // 获取当前进程名
        val processName = CommonUtil.getProcessName(android.os.Process.myPid())
        Beta.upgradeStateListener = object : UpgradeStateListener {
            override fun onDownloadCompleted(isManual: Boolean) {
            }

            override fun onUpgradeSuccess(isManual: Boolean) {
            }

            override fun onUpgradeFailed(isManual: Boolean) {
                if (isManual) {
                    showToast(getString(R.string.check_version_fail))
                }
            }

            override fun onUpgrading(isManual: Boolean) {
                if (isManual) {
                    showToast(getString(R.string.check_version_ing))
                }
            }

            override fun onUpgradeNoVersion(isManual: Boolean) {
                if (isManual) {
                    showToast(getString(R.string.check_no_version))
                }
            }
        }
        // 设置是否为上报进程
        val strategy = CrashReport.UserStrategy(applicationContext)
        strategy.isUploadProcess = false || processName == packageName
        Bugly.init(applicationContext, Constant.BUGLY_ID, BuildConfig.DEBUG, strategy)
    }

    private fun initLitePal() {
        LitePal.initialize(this)
        NewsTypeDao.updateLocalData(this)
    }


    /**
     * 初始化配置
     */
    private fun initConfig() {
        val formatStrategy = PrettyFormatStrategy.newBuilder()
            .showThreadInfo(false)  // 隐藏线程信息 默认：显示
            .methodCount(0)         // 决定打印多少行（每一行代表一个方法）默认：2
            .methodOffset(10)        // (Optional) Hides internal method calls up to offset. Default 5
            .tag("cyc")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
            .build()
        Logger.addLogAdapter(object : AndroidLogAdapter(formatStrategy) {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG
            }
        })

        CrashHandler.instance.init(this)
    }
}
