package com.marvelcomics.brito.data.entity

import java.io.Serializable

data class SeriesEntity(
    private val id: Int = 0,
    private val title: String? = null,
    private val description: String? = null,
    private val thumbnailEntity: ThumbnailEntity? = null
) : Serializable