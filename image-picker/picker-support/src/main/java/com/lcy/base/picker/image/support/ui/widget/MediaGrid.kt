package com.lcy.base.picker.image.support.ui.widget

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.text.format.DateUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.lcy.base.picker.image.support.R
import com.lcy.base.picker.image.support.entity.Item
import com.lcy.base.picker.image.support.entity.SelectionSpec

open class MediaGrid : SquareFrameLayout, View.OnClickListener {

    protected lateinit var mThumbnail: ImageView
    protected lateinit var mCheckView: CheckView
    protected lateinit var mGifTag: ImageView
    protected lateinit var mVideoDuration: TextView

    lateinit var media: Item
    protected lateinit var mPreBindInfo: PreBindInfo
    protected var mListener: OnMediaGridClickListener? = null

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context)
    }

    open fun getLayoutRes(): Int {
        return R.layout.media_grid_content
    }

    fun init(context: Context) {
        LayoutInflater.from(context).inflate(getLayoutRes(), this, true)

        mThumbnail = findViewById(R.id.media_thumbnail)
        mCheckView = findViewById(R.id.check_view)
        mGifTag = findViewById(R.id.gif)
        mVideoDuration = findViewById(R.id.video_duration)

        mThumbnail.setOnClickListener(this)
        mCheckView.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        if (mListener != null) {
            if (v === mThumbnail) {
                mListener!!.onThumbnailClicked(mThumbnail, media, mPreBindInfo.mViewHolder)
            } else if (v === mCheckView) {
                mListener!!.onCheckViewClicked(mCheckView, media, mPreBindInfo.mViewHolder)
            }
        }
    }

    fun preBindMedia(info: PreBindInfo) {
        mPreBindInfo = info
    }

    fun bindMedia(item: Item) {
        media = item
        setGifTag()
        initCheckView()
        setImage()
        setVideoDuration()
    }

    private fun setGifTag() {
        mGifTag.visibility = if (media.isGif) View.VISIBLE else View.GONE
    }

    private fun initCheckView() {
        mCheckView.setCountable(mPreBindInfo.mCheckViewCountable)
    }

    fun setCheckEnabled(enabled: Boolean) {
        mCheckView.isEnabled = enabled
    }

    fun setCheckedNum(checkedNum: Int) {
        mCheckView.setCheckedNum(checkedNum)
    }

    fun setChecked(checked: Boolean) {
        mCheckView.setChecked(checked)
    }

    private fun setImage() {
        if (media.isGif) {
            SelectionSpec.instance.imageEngine.loadGifThumbnail(
                context, mPreBindInfo.mResize,
                mPreBindInfo.mPlaceholder, mThumbnail, media.contentUri
            )
        } else {
            SelectionSpec.instance.imageEngine.loadThumbnail(
                context, mPreBindInfo.mResize,
                mPreBindInfo.mPlaceholder, mThumbnail, media.contentUri
            )
        }
    }

    private fun setVideoDuration() {
        if (media.isVideo) {
            mVideoDuration.visibility = View.VISIBLE
            mVideoDuration.text = DateUtils.formatElapsedTime(media.duration / 1000)
        } else {
            mVideoDuration.visibility = View.GONE
        }
    }

    fun setOnMediaGridClickListener(listener: OnMediaGridClickListener) {
        mListener = listener
    }

    fun removeOnMediaGridClickListener() {
        mListener = null
    }

    interface OnMediaGridClickListener {

        fun onThumbnailClicked(thumbnail: ImageView, item: Item, holder: RecyclerView.ViewHolder)

        fun onCheckViewClicked(checkView: CheckView, item: Item, holder: RecyclerView.ViewHolder)
    }

    class PreBindInfo(
        internal var mResize: Int, internal var mPlaceholder: Drawable, internal var mCheckViewCountable: Boolean,
        internal var mViewHolder: RecyclerView.ViewHolder
    )

}
