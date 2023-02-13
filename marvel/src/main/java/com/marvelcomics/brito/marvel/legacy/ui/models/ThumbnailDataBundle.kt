package com.marvelcomics.brito.marvel.legacy.ui.models

import android.os.Parcelable
import com.marvelcomics.brito.entity.ThumbnailEntity
import kotlinx.parcelize.Parcelize

@Parcelize
class ThumbnailDataBundle(
    val path: String? = null,
    val extension: String? = null
) : Parcelable

fun ThumbnailEntity.fromEntityToBundle(): ThumbnailDataBundle {
    return ThumbnailDataBundle(
        path = path,
        extension = extension
    )
}

fun ThumbnailDataBundle.fromBundleToEntity(): ThumbnailEntity {
    return ThumbnailEntity(
        path = path,
        extension = extension
    )
}
