package com.lcy.base.picker.image.support.ui

import android.os.Bundle

import com.lcy.base.picker.image.support.entity.Item
import com.lcy.base.picker.image.support.model.SelectedItemCollection

open class SelectedPreviewActivity : BasePreviewActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bundle = intent.getBundleExtra(EXTRA_DEFAULT_BUNDLE)
        val selected = bundle.getParcelableArrayList<Item>(SelectedItemCollection.STATE_SELECTION)
        mAdapter.addAll(selected)
        mAdapter.notifyDataSetChanged()
        if (mSpec.countable) {
            mCheckView.setCheckedNum(1)
        } else {
            mCheckView.setChecked(true)
        }
        mPreviousPos = 0
        updateSize(selected!![0])
    }
}
