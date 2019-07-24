package com.release.mvp_kt.ui.page.news_page

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.release.mvp_kt.R
import com.release.mvp_kt.base.BaseMvpActivity
import com.release.mvp_kt.constant.Constant.PHOTO_SET_KEY
import com.release.mvp_kt.mvp.contract.PhotoAlbumContract
import com.release.mvp_kt.mvp.model.bean.PhotoSetInfoBean
import com.release.mvp_kt.mvp.model.bean.PhotosBean
import com.release.mvp_kt.mvp.presenter.PhotoAlbumPresenter
import com.release.mvp_kt.ui.adpater.PhotoSetAdapter
import com.release.mvp_kt.utils.StatusBarUtil
import kotlinx.android.synthetic.main.activity_photo_album.*
import kotlinx.android.synthetic.main.activity_photo_album.view.*
import java.util.*


/**
 * 图集
 *
 * @author Mr.release
 * @create 2019/4/16
 * @Describe
 */
class PhotoAlbumActivity : BaseMvpActivity<PhotoAlbumContract.View, PhotoAlbumContract.Presenter>(),
    PhotoAlbumContract.View {
    override fun createPresenter(): PhotoAlbumContract.Presenter = PhotoAlbumPresenter()

    override fun initLayoutID(): Int = R.layout.activity_photo_album

    private val mAdapter: PhotoSetAdapter by lazy {
        PhotoSetAdapter(this)
    }

    private var mIsHideToolbar = false
    private var mPhotos: List<PhotosBean>? = null
    private var photoSetId: String? = null

    override fun initData() {
        photoSetId = intent.getStringExtra(PHOTO_SET_KEY)

    }

    override fun initView() {
        super.initView()
        tool_bar.setToolBarBackgroundColor(R.color.transparent)
            .setBackDrawable(R.drawable.toolbar_back_white)
            .setTitleColor(R.color.white)
    }

    override fun startNet() {
        mPresenter?.requestData(photoSetId!!)
    }

    override fun loadData(data: PhotoSetInfoBean) {
        tool_bar.setTitleText(data.setname)
        val imgUrls = ArrayList<String>()
        mPhotos = data.photos
        for (photo in mPhotos!!) {
            imgUrls.add(photo.imgurl)
        }

        mAdapter.run {
            setData(imgUrls)
            setTapListener(object : PhotoSetAdapter.OnTapListener {
                override fun onPhotoClick() {
                    mIsHideToolbar = !mIsHideToolbar
                    if (mIsHideToolbar) {
                        drag_layout!!.scrollOutScreen(300)
                        tool_bar!!.animate().translationY((-tool_bar!!.bottom).toFloat()).duration = 300
                    } else {
                        drag_layout!!.scrollInScreen(300)
                        tool_bar!!.animate().translationY(0f).duration = 300
                    }
                }
            })
        }


        vp_photo.run {
            adapter = mAdapter
            offscreenPageLimit = imgUrls.size
            addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
                override fun onPageSelected(position: Int) {
                    tv_content!!.text = mPhotos!![position].note
                    tv_index!!.text = (position + 1).toString() + "/"
                }
            })
        }

        tv_count!!.text = mPhotos!!.size.toString() + ""
        tv_index!!.text = 1.toString() + "/"
        tv_content!!.text = mPhotos!![0].note

    }

    override fun initThemeColor() {
        super.initThemeColor()
        StatusBarUtil.setColorNoTranslucent(this, ContextCompat.getColor(this, R.color.black))
    }


    companion object {

        fun start(context: Context, newsId: String) {
            val intent = Intent(context, PhotoAlbumActivity::class.java)
            intent.putExtra(PHOTO_SET_KEY, newsId)
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            context.startActivity(intent)
            (context as Activity).overridePendingTransition(R.anim.slide_right_entry, R.anim.hold)
        }
    }
}
