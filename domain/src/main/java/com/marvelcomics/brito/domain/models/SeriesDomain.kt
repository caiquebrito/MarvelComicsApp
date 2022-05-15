package com.marvelcomics.brito.domain.models

data class SeriesDomain(
    val id: Int = 0,
    val title: String? = null,
    val description: String? = null,
    val thumbnailDomain: ThumbnailDomain? = null
)
