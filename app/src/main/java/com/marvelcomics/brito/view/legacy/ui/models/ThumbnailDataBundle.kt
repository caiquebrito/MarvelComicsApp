package com.marvelcomics.brito.view.legacy.ui.models

import android.os.Parcelable
import com.marvelcomics.brito.entity.ThumbnailEntity
import kotlinx.parcelize.Parcelize

@Parcelize
class ThumbnailDataBundle(
    val path: String? = null,
    val extension: String? = null
) : Parcelable

fun ThumbnailEntity.toDataBundle(): ThumbnailDataBundle {
    return ThumbnailDataBundle(
        path = path,
        extension = extension
    )
}

fun ThumbnailDataBundle.fromDataBundle(): ThumbnailEntity {
    return ThumbnailEntity(
        path = path,
        extension = extension
    )
}
