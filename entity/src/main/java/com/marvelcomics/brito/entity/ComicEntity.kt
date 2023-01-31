package com.marvelcomics.brito.entity

data class ComicEntity(
    val id: Int = 0,
    val title: String? = null,
    val description: String? = null,
    val thumbnailEntity: ThumbnailEntity? = null
)
