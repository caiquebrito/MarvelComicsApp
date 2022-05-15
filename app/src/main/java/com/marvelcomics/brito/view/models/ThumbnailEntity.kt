package com.marvelcomics.brito.view.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ThumbnailEntity(
    val path: String? = null,
    val extension: String? = null
) : Parcelable {
    fun getFullUrlThumbnailWithAspect(aspect: String): String {
        return if (aspect.isEmpty()) {
            path.toString() + "." + extension
        } else {
            path.toString() + "/" + aspect + "." + extension
        }
    }
}
