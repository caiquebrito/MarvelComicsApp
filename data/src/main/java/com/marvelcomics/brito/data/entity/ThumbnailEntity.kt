package com.marvelcomics.brito.data.entity

import java.io.Serializable

data class ThumbnailEntity(val path: String? = null,
                           val extension: String? = null) : Serializable