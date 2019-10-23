package com.lcy.base.picker.image.support.ui.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.ViewGroup
import com.lcy.base.picker.image.support.entity.Item
import com.lcy.base.picker.image.support.ui.PreviewItemFragment

class PreviewPagerAdapter internal constructor(
    manager: FragmentManager,
    private val mListener: OnPrimaryItemSetListener?
) : FragmentPagerAdapter(manager) {

    private val mItems = ArrayList<Item>()

    override fun getItem(position: Int): Fragment {
        return PreviewItemFragment.newInstance(mItems[position])
    }

    override fun getCount(): Int {
        return mItems.size
    }

    override fun setPrimaryItem(container: ViewGroup, position: Int, `object`: Any) {
        super.setPrimaryItem(container, position, `object`)
        mListener?.onPrimaryItemSet(position)
    }

    fun getMediaItem(position: Int): Item {
        return mItems[position]
    }

    fun addAll(items: List<Item>) {
        mItems.addAll(items)
    }

    internal interface OnPrimaryItemSetListener {

        fun onPrimaryItemSet(position: Int)
    }
}