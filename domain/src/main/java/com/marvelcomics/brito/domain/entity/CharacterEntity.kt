package com.marvelcomics.brito.domain.entity

import java.io.Serializable

class CharacterEntity(
    val id: Int,
    val name: String?,
    val description: String?,
    private val thumbnailEntity: ThumbnailEntity?
) : Serializable {
    fun getFullUrlThumbnailWithAspect(aspect: String): String? {
        return if (aspect.isEmpty()) {
            thumbnailEntity?.path.toString() + "." + thumbnailEntity?.extension
        } else {
            thumbnailEntity?.path.toString() + "/" + aspect + "." + thumbnailEntity?.extension
        }
    }
}