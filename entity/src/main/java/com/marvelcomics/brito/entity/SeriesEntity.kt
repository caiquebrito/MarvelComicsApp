package com.marvelcomics.brito.entity

data class SeriesEntity(
    val id: Int = 0,
    val title: String? = null,
    val description: String? = null,
    val thumbnailDomain: ThumbnailEntity? = null
)
