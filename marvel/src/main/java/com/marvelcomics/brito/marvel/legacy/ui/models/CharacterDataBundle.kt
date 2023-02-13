package com.marvelcomics.brito.marvel.legacy.ui.models

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

fun CharacterEntity.fromEntityToBundle(): CharacterDataBundle {
    return CharacterDataBundle(
        id = id,
        name = name,
        description = description,
        thumbnailDataBundle = thumbnailEntity?.fromEntityToBundle()
    )
}

fun CharacterDataBundle.fromBundleToEntity(): CharacterEntity {
    return CharacterEntity(
        id = id,
        name = name,
        description = description,
        thumbnailEntity = thumbnailDataBundle?.fromBundleToEntity()
    )
}
