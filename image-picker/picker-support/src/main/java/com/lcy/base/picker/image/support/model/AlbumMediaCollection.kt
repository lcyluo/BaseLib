package com.lcy.base.picker.image.support.model

import android.content.Context
import android.database.Cursor
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v4.app.LoaderManager
import android.support.v4.content.Loader

import com.lcy.base.picker.image.support.entity.Album
import com.lcy.base.picker.image.support.loader.AlbumMediaLoader

import java.lang.ref.WeakReference

class AlbumMediaCollection : LoaderManager.LoaderCallbacks<Cursor> {
    private var mContext: WeakReference<Context>? = null
    private var mLoaderManager: LoaderManager? = null
    private var mCallbacks: AlbumMediaCallbacks? = null

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        val context = mContext!!.get()
        val album = args!!.getParcelable<Album>(ARGS_ALBUM)

        return AlbumMediaLoader.newInstance(
            context, album,
            album.isAll && args.getBoolean(ARGS_ENABLE_CAPTURE, false)
        )
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor) {
        mContext?.get() ?: return

        mCallbacks!!.onAlbumMediaLoad(data)
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        mContext?.get() ?: return

        mCallbacks!!.onAlbumMediaReset()
    }

    fun onCreate(context: FragmentActivity, callbacks: AlbumMediaCallbacks) {
        mContext = WeakReference(context)
        mLoaderManager = context.supportLoaderManager
        mCallbacks = callbacks
    }

    fun onDestroy() {
        mLoaderManager!!.destroyLoader(LOADER_ID)
        mCallbacks = null
    }

    @JvmOverloads
    fun load(target: Album?, enableCapture: Boolean = false) {
        val args = Bundle()
        args.putParcelable(ARGS_ALBUM, target)
        args.putBoolean(ARGS_ENABLE_CAPTURE, enableCapture)
        mLoaderManager!!.initLoader(LOADER_ID, args, this)
    }

    interface AlbumMediaCallbacks {

        fun onAlbumMediaLoad(cursor: Cursor)

        fun onAlbumMediaReset()
    }

    companion object {
        private const val LOADER_ID = 2
        private const val ARGS_ALBUM = "args_album"
        private const val ARGS_ENABLE_CAPTURE = "args_enable_capture"
    }
}
