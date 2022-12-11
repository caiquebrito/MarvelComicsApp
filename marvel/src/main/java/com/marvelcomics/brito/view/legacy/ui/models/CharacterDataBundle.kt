package com.marvelcomics.brito.view.legacy.ui.models

import android.os.Parcelable
import com.marvelcomics.brito.entity.CharacterEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class CharacterDataBundle(
    val id: Int,
    val name: String?,
    val description: String?,
    val thumbnailDataBundle: ThumbnailDataBundle?
) : Parcelable

fun CharacterEntity.toDataBundle(): CharacterDataBundle {
    return CharacterDataBundle(
        id = id,
        name = name,
        description = description,
        thumbnailDataBundle = thumbnailEntity?.toDataBundle()
    )
}

fun CharacterDataBundle.fromDataBundle(): CharacterEntity {
    return CharacterEntity(
        id = id,
        name = name,
        description = description,
        thumbnailEntity = thumbnailDataBundle?.fromDataBundle()
    )
}
