package com.lcy.base.picker.image.support.entity

import android.content.Context
import android.database.Cursor
import android.os.Parcel
import android.os.Parcelable
import android.provider.MediaStore
import com.lcy.base.picker.image.support.R
import com.lcy.base.picker.image.support.loader.AlbumLoader

class Album : Parcelable {

    val id: String
    val coverPath: String
    private val mDisplayName: String
    var count: Long = 0
        private set

    val isAll: Boolean
        get() = ALBUM_ID_ALL == id

    val isEmpty: Boolean
        get() = count == 0L

    internal constructor(id: String, coverPath: String, albumName: String, count: Long) {
        this.id = id
        this.coverPath = coverPath
        mDisplayName = albumName
        this.count = count
    }

    internal constructor(source: Parcel) {
        id = source.readString()!!
        coverPath = source.readString()!!
        mDisplayName = source.readString()!!
        count = source.readLong()
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(id)
        dest.writeString(coverPath)
        dest.writeString(mDisplayName)
        dest.writeLong(count)
    }

    fun addCaptureCount() {
        count++
    }

    fun getDisplayName(context: Context): String {
        return if (isAll) {
            context.getString(R.string.album_name_all)
        } else mDisplayName
    }

    companion object {

        @JvmField
        val CREATOR: Parcelable.Creator<Album> = object : Parcelable.Creator<Album> {
            override fun createFromParcel(source: Parcel): Album? {
                return Album(source)
            }

            override fun newArray(size: Int): Array<Album?> {
                return arrayOfNulls(size)
            }
        }
        const val ALBUM_ID_ALL = (-1).toString()
        const val ALBUM_NAME_ALL = "All"

        /**
         * Constructs a new [Album] entity from the [Cursor].
         * This method is not responsible for managing cursor resource, such as close, iterate, and so on.
         */
        fun valueOf(cursor: Cursor): Album {
            return Album(
                cursor.getString(cursor.getColumnIndex("bucket_id")),
                cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA)),
                cursor.getString(cursor.getColumnIndex("bucket_display_name")),
                cursor.getLong(cursor.getColumnIndex(AlbumLoader.COLUMN_COUNT))
            )
        }
    }

}