package com.marvelcomics.brito.view.extensions

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface

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
