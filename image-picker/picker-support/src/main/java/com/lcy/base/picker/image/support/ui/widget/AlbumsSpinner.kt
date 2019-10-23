package com.lcy.base.picker.image.support.ui.widget

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.graphics.PorterDuff
import android.os.Build
import android.view.View
import android.widget.AdapterView
import android.widget.CursorAdapter
import android.widget.ListPopupWindow
import android.widget.TextView
import com.lcy.base.picker.image.support.R

import com.lcy.base.picker.image.support.entity.Album
import com.lcy.base.picker.image.support.utils.Platform

open class AlbumsSpinner {

    protected lateinit var mAdapter: CursorAdapter
    protected lateinit var mSelected: TextView
    protected lateinit var mListPopupWindow: ListPopupWindow
    protected var mOnItemSelectedListener: AdapterView.OnItemSelectedListener? = null

    constructor()

    constructor(context: Context) {
        mListPopupWindow = ListPopupWindow(context, null, R.attr.listPopupWindowStyle)
        mListPopupWindow.isModal = true
        val density = context.resources.displayMetrics.density
        mListPopupWindow.setContentWidth((216 * density).toInt())
        mListPopupWindow.horizontalOffset = (16 * density).toInt()
        mListPopupWindow.verticalOffset = (-48 * density).toInt()

        mListPopupWindow.setOnItemClickListener { parent, view, position, id ->
            this@AlbumsSpinner.onItemSelected(parent.context, position)
            if (mOnItemSelectedListener != null) {
                mOnItemSelectedListener!!.onItemSelected(parent, view, position, id)
            }
        }
    }

    fun setOnItemSelectedListener(listener: AdapterView.OnItemSelectedListener) {
        mOnItemSelectedListener = listener
    }

    fun setSelection(context: Context, position: Int) {
        mListPopupWindow.setSelection(position)
        onItemSelected(context, position)
    }

    protected fun onItemSelected(context: Context, position: Int) {
        mListPopupWindow.dismiss()
        val cursor = mAdapter.cursor
        cursor.moveToPosition(position)
        val album = Album.valueOf(cursor)
        val displayName = album.getDisplayName(context)
        if (mSelected.visibility == View.VISIBLE) {
            mSelected.text = displayName
        } else {
            if (Platform.hasICS()) {
                mSelected.alpha = 0.0f
                mSelected.visibility = View.VISIBLE
                mSelected.text = displayName
                mSelected.animate().alpha(1.0f).setDuration(
                    context.resources.getInteger(
                        android.R.integer.config_longAnimTime
                    ).toLong()
                ).start()
            } else {
                mSelected.visibility = View.VISIBLE
                mSelected.text = displayName
            }

        }
    }

    fun setAdapter(adapter: CursorAdapter) {
        mListPopupWindow.setAdapter(adapter)
        mAdapter = adapter
    }

    @SuppressLint("ClickableViewAccessibility")
    @TargetApi(Build.VERSION_CODES.KITKAT)
    fun setSelectedTextView(textView: TextView) {
        mSelected = textView
        // tint dropdown arrow icon
        val drawables = mSelected.compoundDrawables
        val right = drawables[2]
        val ta = mSelected.context.theme.obtainStyledAttributes(
            intArrayOf(R.attr.album_element_color)
        )
        val color = ta.getColor(0, 0)
        ta.recycle()
        right.setColorFilter(color, PorterDuff.Mode.SRC_IN)

        mSelected.visibility = View.GONE
        mSelected.setOnClickListener { v ->
            val itemHeight = v.resources.getDimensionPixelSize(R.dimen.album_item_height)
            mListPopupWindow.height = if (mAdapter.count > MAX_SHOWN_COUNT)
                itemHeight * MAX_SHOWN_COUNT
            else
                itemHeight * mAdapter.count
            mListPopupWindow.show()
        }
        mSelected.setOnTouchListener(mListPopupWindow.createDragToOpenListener(mSelected))
    }

    fun setPopupAnchorView(view: View) {
        mListPopupWindow.anchorView = view
    }

    companion object {

        protected const val MAX_SHOWN_COUNT = 6
    }

}
