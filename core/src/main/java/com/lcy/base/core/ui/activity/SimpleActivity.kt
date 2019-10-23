package com.lcy.base.core.ui.activity

import android.os.Build
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v7.app.ActionBar
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.TextView
import com.lcy.base.core.R

abstract class SimpleActivity : BaseAppCompatActivity() {

    protected var mToolbar: Toolbar? = null
    protected var mAppBarLayout: AppBarLayout? = null
    protected var mAppBarLine: View? = null

    protected var mActionBarHelper: ActionBarHelper? = null

    override fun initToolbar(savedInstanceState: Bundle?) {
        mToolbar = findViewById(R.id.toolbar)
        mAppBarLayout = findViewById(R.id.app_bar_layout)
        mAppBarLine = findViewById(R.id.app_bar_line)

        initToolbarHelper()
    }

    /**
     * init the toolbar
     */
    private fun initToolbarHelper() {
        if (this.mToolbar == null || this.mAppBarLayout == null) return
        this.setSupportActionBar(this.mToolbar)
        this.mActionBarHelper = this.createActionBarHelper()
        this.mActionBarHelper!!.init()
        if (Build.VERSION.SDK_INT >= 21) {
            this.mAppBarLayout!!.elevation = 4f
        }
    }

    override fun onTitleChanged(title: CharSequence, color: Int) {
        super.onTitleChanged(title, color)
        mActionBarHelper?.setTitle(title)
    }

    /**
     * set the AppBarLayout alpha
     *
     * @param alpha alpha
     */
    protected fun setAppBarLayoutAlpha(alpha: Float) {
        if (mAppBarLayout == null) return
        this.mAppBarLayout!!.alpha = alpha
    }

    /**
     * set the AppBarLayout visibility
     *
     * @param visibility visibility
     */
    protected fun setAppBarLayoutVisibility(visibility: Boolean) {
        if (mAppBarLayout == null) return
        if (visibility) {
            this.mAppBarLayout!!.visibility = View.VISIBLE
        } else {
            this.mAppBarLayout!!.visibility = View.GONE
        }
    }

    protected fun setAppBarLineVisibility(visibility: Boolean) {
        if (mAppBarLine == null) return
        this.mAppBarLine!!.visibility = if (visibility) View.VISIBLE else View.GONE
    }

    /**
     * Create a compatible helper that will manipulate the action bar if available.
     */
    private fun createActionBarHelper(): ActionBarHelper {
        return ActionBarHelper()
    }


    inner class ActionBarHelper {
        private var mActionBar: ActionBar? = null
        private var mDrawerTitle: CharSequence? = null
        private var mTitle: CharSequence? = null
        private var toolbarTitle: TextView? = null

        init {
            this.mActionBar = supportActionBar
        }

        fun init() {
            if (this.mActionBar == null || mToolbar == null) return
            this.mActionBar!!.setDisplayHomeAsUpEnabled(true)
            this.mActionBar!!.setDisplayShowHomeEnabled(false)
            mDrawerTitle = title
            this.mTitle = mDrawerTitle
            toolbarTitle = mToolbar!!.findViewById(R.id.toolbar_title) as TextView
            if (toolbarTitle != null && supportActionBar != null) {
                supportActionBar!!.setDisplayShowTitleEnabled(false)
                toolbarTitle!!.text = mTitle
            }
        }


        fun onDrawerClosed() {
            this.mActionBar?.title = this.mTitle
        }


        fun onDrawerOpened() {
            if (this.mActionBar == null) return
            this.mActionBar?.title = this.mDrawerTitle
        }


        fun setTitle(title: CharSequence) {
            this.mTitle = title
            toolbarTitle?.text = mTitle
        }


        fun setDrawerTitle(drawerTitle: CharSequence) {
            this.mDrawerTitle = drawerTitle
        }


        fun setDisplayHomeAsUpEnabled(showHomeAsUp: Boolean) {
            this.mActionBar?.setDisplayHomeAsUpEnabled(showHomeAsUp)
        }
    }

    override fun initInject() {}

    override fun initPresenter() {}
}