package com.marvelcomics.brito.view.legacy.ui

import android.content.Context
import android.content.Intent

interface ActivityArgs {
    fun build(context: Context): Intent?
    fun launch(context: Context) = context.startActivity(build(context))
}
