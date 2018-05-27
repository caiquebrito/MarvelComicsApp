package com.marvelcomics.brito.infrastructure.utils;

import android.app.AlertDialog;
import android.content.Context;

public class AlertDialogUtils {

    public static void showSimpleDialog(String title, String message, Context context) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setNeutralButton("Ok", (dialog, which) -> dialog.dismiss())
                .show();
    }
}
