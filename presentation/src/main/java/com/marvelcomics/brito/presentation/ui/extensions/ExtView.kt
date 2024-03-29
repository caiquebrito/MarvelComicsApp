package com.marvelcomics.brito.presentation.ui.extensions

import android.content.res.Resources
import android.util.DisplayMetrics
import kotlin.math.roundToInt

fun Int.dpToPx(resources: Resources): Int {
    val displayMetrics = resources.displayMetrics
    return (this * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT)).roundToInt()
}
