package com.release.mvp_kt.ui.adpater

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.github.ybq.android.spinkit.SpinKitView
import com.release.mvp_kt.R
import com.release.mvp_kt.utils.ImageLoader
import uk.co.senab.photoview.PhotoView
import uk.co.senab.photoview.PhotoViewAttacher


/**
 * @author Mr.release
 * @create 2019/4/15
 * @Describe
 */
class PhotoSetAdapter(private val mContext: Context) : PagerAdapter() {

    private var mImgList: List<String>? = null
    private var mTapListener: OnTapListener? = null

    fun setData(imgList: List<String>) {
        this.mImgList = imgList
    }

    override fun getCount(): Int {
        return mImgList!!.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(mContext).inflate(R.layout.adapter_photo_pager, null, false)
        val photo = view.findViewById<View>(R.id.iv_photo) as PhotoView
        val loadingView = view.findViewById<View>(R.id.loading_view) as SpinKitView
        val tvReload = view.findViewById<View>(R.id.tv_reload) as TextView


        val requestListener = object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any,
                target: Target<Drawable>,
                isFirstResource: Boolean
            ): Boolean {
                loadingView.visibility = View.GONE
                tvReload.visibility = View.VISIBLE
                return false
            }

            override fun onResourceReady(
                resource: Drawable,
                model: Any,
                target: Target<Drawable>,
                dataSource: DataSource,
                isFirstResource: Boolean
            ): Boolean {
                loadingView.visibility = View.GONE
                tvReload.visibility = View.GONE
                photo.setImageDrawable(resource)
                return true
            }
        }

        ImageLoader.loadFitCenter(mContext, mImgList!![position % mImgList!!.size], photo, requestListener)
        photo.onPhotoTapListener = PhotoViewAttacher.OnPhotoTapListener { _, _, _ ->
            if (mTapListener != null) {
                mTapListener!!.onPhotoClick()
            }
        }
        tvReload.setOnClickListener {
            tvReload.visibility = View.GONE
            loadingView.visibility = View.VISIBLE
            ImageLoader.loadFitCenter(mContext, mImgList!![position % mImgList!!.size], photo, requestListener)
        }
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    fun setTapListener(listener: OnTapListener) {
        mTapListener = listener
    }

    interface OnTapListener {
        fun onPhotoClick()
    }
}
