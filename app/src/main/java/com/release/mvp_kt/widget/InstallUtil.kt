package com.release.mvp_kt.widget

import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.pm.PackageManager.NameNotFoundException
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings
import android.text.TextUtils
import androidx.core.content.FileProvider
import com.release.mvp_kt.R

import java.io.File

object InstallUtil {

    private val TAG = "InstallUtil"
    private val PACKAGE_URL_SCHEME = "package:"
    private var versionCode: Int = 0
    private var versionName: String? = null

    /**
     * 是否已安装app
     *
     * @param context
     * @param packageName
     * @return
     */
    fun isAppInstalled(context: Context, packageName: String): Boolean {
        try {
            return if (TextUtils.isEmpty(packageName)) false else context.packageManager.getPackageInfo(
                packageName,
                PackageManager.GET_ACTIVITIES
            ) != null
        } catch (localNameNotFoundException: NameNotFoundException) {
            return false
        }

    }

    /**
     * 打开app
     *
     * @param packageName
     * @param context
     */
    fun openApp(context: Context, packageName: String) {
        val packageManager = context.packageManager
        val intent = packageManager.getLaunchIntentForPackage(packageName)
        if (intent != null)
            context.startActivity(intent)
    }

    /**
     * 某个app的版本号，未安装时返回null
     */
    fun getVersionName(context: Context, packageName: String): String? {
        try {
            val pi = context.packageManager.getPackageInfo(packageName, 0)
            return pi?.versionName
        } catch (e: NameNotFoundException) {
            return null
        }

    }

    fun getVersionCode(context: Context): Int {
        if (versionCode == 0) {
            loadVersionInfo(context)
        }

        return versionCode
    }

    /**
     * 易信版本号
     */
    fun getVersionName(context: Context): String? {
        if (TextUtils.isEmpty(versionName)) {
            loadVersionInfo(context)
        }

        return versionName
    }

    private fun loadVersionInfo(context: Context) {
        try {
            val pi = context.packageManager.getPackageInfo(context.packageName, 0)
            if (pi != null) {
                versionCode = pi.versionCode
                versionName = pi.versionName
            }
        } catch (e: NameNotFoundException) {
            e.printStackTrace()
        }

    }

    /**
     * 安装apk文件
     */
    fun installApk(context: Context, filepath: String) {
        context.startActivity(getInstallApkIntent(filepath))
    }

    /**
     * 安装apk文件
     */
    fun getInstallApkIntent(filepath: String): Intent {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        val file = File(filepath)
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive")
        return intent
    }

    fun install(context: Context) {
        val path = Environment.getExternalStorageDirectory().toString() + File.separator +
                context.packageName + File.separator + "apk" + File.separator
        val file = File(path, context.resources.getString(R.string.app_name) + ".apk")
        val intent = Intent(Intent.ACTION_VIEW)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

            val apkUri = FileProvider.getUriForFile(context, context.packageName + ".fileprovider", file)
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive")
        } else {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive")
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        context.startActivity(intent)
    }

    /**
     * 启动应用的设置
     */
    fun startAppSettings(context: Context) {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.data = Uri.parse(PACKAGE_URL_SCHEME + context.packageName)
        context.startActivity(intent)
    }
}
