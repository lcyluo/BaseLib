package com.lcy.base.picker.image.support.ui

import android.database.Cursor
import android.os.Bundle


import com.lcy.base.picker.image.support.entity.Album
import com.lcy.base.picker.image.support.entity.Item
import com.lcy.base.picker.image.support.model.AlbumMediaCollection
import com.lcy.base.picker.image.support.ui.adapter.PreviewPagerAdapter

import java.util.ArrayList

open class AlbumPreviewActivity : BasePreviewActivity(), AlbumMediaCollection.AlbumMediaCallbacks {

    private val mCollection = AlbumMediaCollection()

    private var mIsAlreadySetPosition: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mCollection.onCreate(this, this)
        val album = intent.getParcelableExtra<Album>(EXTRA_ALBUM)
        mCollection.load(album)

        val item = intent.getParcelableExtra<Item>(EXTRA_ITEM)
        if (mSpec.countable) {
            mCheckView.setCheckedNum(mSelectedCollection.checkedNumOf(item))
        } else {
            mCheckView.setChecked(mSelectedCollection.isSelected(item))
        }
        updateSize(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        mCollection.onDestroy()
    }

    override fun onAlbumMediaLoad(cursor: Cursor) {
        val items = ArrayList<Item>()
        while (cursor.moveToNext()) {
            items.add(Item.valueOf(cursor))
        }
        val adapter = mPager.adapter as PreviewPagerAdapter
        adapter.addAll(items)
        adapter.notifyDataSetChanged()
        if (!mIsAlreadySetPosition) {
            //onAlbumMediaLoad is called many times..
            mIsAlreadySetPosition = true
            val selected = intent.getParcelableExtra<Item>(EXTRA_ITEM)
            val selectedIndex = items.indexOf(selected)
            mPager.setCurrentItem(selectedIndex, false)
            mPreviousPos = selectedIndex
        }
    }

    override fun onAlbumMediaReset() {

    }

    companion object {

        const val EXTRA_ALBUM = "extra_album"
        const val EXTRA_ITEM = "extra_item"
    }
}
