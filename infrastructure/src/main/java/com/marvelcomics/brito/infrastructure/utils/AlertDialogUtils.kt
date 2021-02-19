package com.marvelcomics.brito.infrastructure.utils

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog

object AlertDialogUtils {
    fun showSimpleDialog(
        title: String?,
        message: String?,
        context: Context?
    ) {
        AlertDialog.Builder(context!!)
            .setTitle(title)
            .setMessage(message)
            .setCancelable(false)
            .setNeutralButton(
                "Ok"
            ) { dialog: DialogInterface, _: Int -> dialog.dismiss() }
            .show()
    }
}
