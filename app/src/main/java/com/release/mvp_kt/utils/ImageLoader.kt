@file:Suppress("DEPRECATION")

package com.release.mvp_kt.utils

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.release.mvp_kt.R
import java.util.concurrent.ExecutionException

/**
 * @author Mr.release
 * @create 2019/7/10
 * @Describe
 */
object ImageLoader {


    /**
     * 加载图片
     * @param context
     * @param url
     * @param iv
     */
    fun load(context: Context?, url: String?, iv: ImageView?) {

        iv?.apply {
            Glide.with(context!!).clear(iv)
            val options = RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .placeholder(R.drawable.bg_placeholder)
            Glide.with(context)
                .load(url)
                .transition(DrawableTransitionOptions().crossFade())
                .apply(options)
                .into(iv)
        }
    }


    fun loadFit(context: Context, url: String, view: ImageView, defaultResId: Int) {
        Glide.with(context).load(url).fitCenter().dontAnimate().placeholder(defaultResId).into(view)
    }

    fun loadCenterCrop(context: Context, url: String, view: ImageView, defaultResId: Int) {
        Glide.with(context).load(url).centerCrop().dontAnimate().placeholder(defaultResId).into(view)
    }

    fun loadFitCenter(context: Context, url: String, view: ImageView, defaultResId: Int) {
        Glide.with(context).load(url).fitCenter().dontAnimate().placeholder(defaultResId).into(view)
    }

    /**
     * 带监听处理
     *
     * @param context
     * @param url
     * @param view
     * @param listener
     */
    fun loadFitCenter(context: Context, url: String, view: ImageView, listener: RequestListener<Drawable>) {
        Glide.with(context).load(url).fitCenter().dontAnimate().listener(listener).into(view)
    }

    fun loadCenterCrop(context: Context, url: String, view: ImageView, listener: RequestListener<Drawable>) {
        Glide.with(context).load(url).centerCrop().dontAnimate().listener(listener).into(view)
    }

    /**
     * 设置图片大小处理
     *
     * @param context
     * @param url
     * @param view
     * @param defaultResId
     * @param width
     * @param height
     */
    fun loadFitOverride(context: Context, url: String, view: ImageView, defaultResId: Int, width: Int, height: Int) {
        Glide.with(context).load(url).fitCenter().dontAnimate().override(width, height).placeholder(defaultResId)
            .into(view)
    }

    /**
     * 计算图片分辨率
     *
     * @param context
     * @param url
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Throws(ExecutionException::class, InterruptedException::class)
    fun calePhotoSize(context: Context, url: String): String {
        val file = Glide.with(context).load(url)
            .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get()
        // First decode with inJustDecodeBounds=true to check dimensions
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(file.absolutePath, options)
        return options.outWidth.toString() + "*" + options.outHeight
    }

}