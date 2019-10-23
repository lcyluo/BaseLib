package com.lcy.base.picker.image.support.ui.adapter

import android.database.Cursor
import android.provider.MediaStore
import android.support.v7.widget.RecyclerView

abstract class RecyclerViewCursorAdapter<VH : RecyclerView.ViewHolder> internal constructor(c: Cursor?) : RecyclerView.Adapter<VH>() {

    var cursor: Cursor? = null
        private set
    private var mRowIDColumn: Int = 0

    init {
        setHasStableIds(true)
        swapCursor(c)
    }

    protected abstract fun onBindViewHolder(holder: VH, cursor: Cursor?)

    override fun onBindViewHolder(holder: VH, position: Int) {
        if (!isDataValid(cursor)) {
            throw IllegalStateException("Cannot bind view holder when cursor is in invalid state.")
        }
        if (cursor?.moveToPosition(position) != true) {
            throw IllegalStateException("Could not move cursor to position " + position
                    + " when trying to bind view holder")
        }

        onBindViewHolder(holder, cursor!!)
    }

    override fun getItemViewType(position: Int): Int {
        if (!cursor!!.moveToPosition(position)) {
            throw IllegalStateException("Could not move cursor to position " + position
                    + " when trying to get item view type.")
        }
        return getItemViewType(position, cursor)
    }

    protected abstract fun getItemViewType(position: Int, cursor: Cursor?): Int

    override fun getItemCount(): Int {
        return if (isDataValid(cursor)) {
            cursor!!.count
        } else {
            0
        }
    }

    override fun getItemId(position: Int): Long {
        if (!isDataValid(cursor)) {
            throw IllegalStateException("Cannot lookup item id when cursor is in invalid state.")
        }
        if (!cursor!!.moveToPosition(position)) {
            throw IllegalStateException("Could not move cursor to position " + position
                    + " when trying to get an item id")
        }

        return cursor!!.getLong(mRowIDColumn)
    }

    fun swapCursor(newCursor: Cursor?) {
        if (newCursor === cursor) {
            return
        }

        if (newCursor != null) {
            cursor = newCursor
            mRowIDColumn = cursor!!.getColumnIndexOrThrow(MediaStore.Files.FileColumns._ID)
            // notify the observers about the new cursor
            notifyDataSetChanged()
        } else {
            notifyItemRangeRemoved(0, itemCount)
            cursor = null
            mRowIDColumn = -1
        }
    }

    private fun isDataValid(cursor: Cursor?): Boolean {
        return cursor != null && !cursor.isClosed
    }
}
