package com.lcy.base.picker.image.support.entity

import android.content.Context
import android.support.annotation.IntDef
import android.support.v4.app.FragmentActivity
import android.widget.Toast

import com.lcy.base.picker.image.support.ui.widget.IncapableDialog

import java.lang.annotation.Retention

import java.lang.annotation.RetentionPolicy.SOURCE

@Suppress("DEPRECATED_JAVA_ANNOTATION")
class IncapableCause {

    private var mForm = TOAST
    private var mTitle: String? = null
    private var mMessage: String? = null

    @Retention(SOURCE)
    @IntDef(TOAST, DIALOG, NONE)
    annotation class Form {

    }

    constructor(message: String) {
        mMessage = message
    }

    constructor(title: String, message: String) {
        mTitle = title
        mMessage = message
    }

    constructor(@Form form: Int, message: String) {
        mForm = form
        mMessage = message
    }

    constructor(@Form form: Int, title: String, message: String) {
        mForm = form
        mTitle = title
        mMessage = message
    }

    companion object {
        const val TOAST = 0x00
        const val DIALOG = 0x01
        const val NONE = 0x02

        fun handleCause(context: Context, cause: IncapableCause?) {
            if (cause == null)
                return

            when (cause.mForm) {
                NONE -> {
                }
                DIALOG -> {
                    val incapableDialog = IncapableDialog.newInstance(cause.mTitle!!, cause.mMessage!!)
                    incapableDialog.show(
                        (context as FragmentActivity).supportFragmentManager,
                        IncapableDialog::class.java.name
                    )
                }
                TOAST -> Toast.makeText(context, cause.mMessage, Toast.LENGTH_SHORT).show()
                else -> Toast.makeText(context, cause.mMessage, Toast.LENGTH_SHORT).show()
            }// do nothing.
        }
    }
}
