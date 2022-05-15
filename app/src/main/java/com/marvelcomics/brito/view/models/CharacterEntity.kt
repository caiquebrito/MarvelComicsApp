package com.marvelcomics.brito.view.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class CharacterEntity(
    val id: Int,
    val name: String?,
    val description: String?,
    val thumbnailEntity: ThumbnailEntity?
) : Parcelable
