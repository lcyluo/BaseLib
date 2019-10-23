package com.lcy.base.picker.image.support.ui

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.lcy.base.picker.image.support.R
import com.lcy.base.picker.image.support.entity.Item
import com.lcy.base.picker.image.support.entity.SelectionSpec
import com.lcy.base.picker.image.support.utils.PhotoMetadataUtils
import it.sephiroth.android.library.imagezoom.ImageViewTouch
import it.sephiroth.android.library.imagezoom.ImageViewTouchBase

class PreviewItemFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_preview_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val item = arguments!!.getParcelable<Item>(ARGS_ITEM) ?: return

        val videoPlayButton = view.findViewById<View>(R.id.video_play_button)
        if (item.isVideo) {
            videoPlayButton.visibility = View.VISIBLE
            videoPlayButton.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.setDataAndType(item.contentUri, "video/*")
                try {
                    startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    Toast.makeText(context, R.string.error_no_video_activity, Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            videoPlayButton.visibility = View.GONE
        }

        val image = view.findViewById<ImageViewTouch>(R.id.image_view)
        image.displayType = ImageViewTouchBase.DisplayType.FIT_TO_SCREEN

        val size = PhotoMetadataUtils.getBitmapSize(item.contentUri, activity!!)
        if (item.isGif) {
            SelectionSpec.instance.imageEngine.loadGifImage(
                context!!, size.x, size.y, image,
                item.contentUri
            )
        } else {
            SelectionSpec.instance.imageEngine.loadImage(
                context!!, size.x, size.y, image,
                item.contentUri
            )
        }
    }

    fun resetView() {
        if (view != null) {
            (view!!.findViewById<View>(R.id.image_view) as ImageViewTouch).resetMatrix()
        }
    }

    companion object {

        private const val ARGS_ITEM = "args_item"

        fun newInstance(item: Item): PreviewItemFragment {
            val fragment = PreviewItemFragment()
            val bundle = Bundle()
            bundle.putParcelable(ARGS_ITEM, item)
            fragment.arguments = bundle
            return fragment
        }
    }
}
