package com.marvelcomics.brito.data.ktor

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class ThumbnailResponseK {
    @SerialName(value = "path")
    var path: String? = null

    @SerialName(value = "extension")
    var extension: String? = null
}