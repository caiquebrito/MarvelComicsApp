package com.marvelcomics.brito.domain.entity

import java.io.Serializable

class ComicEntity(
    val id: Int = 0,
    val title: String? = null,
    val description: String? = null,
    private val thumbnailEntity: ThumbnailEntity? = null
) : Serializable {
    fun getFullUrlThumbnailWithAspect(aspect: String): String? {
        return if (aspect.isEmpty()) {
            thumbnailEntity?.path.toString() + "." + thumbnailEntity?.extension
        } else {
            thumbnailEntity?.path.toString() + "/" + aspect + "." + thumbnailEntity?.extension
        }
    }
}
