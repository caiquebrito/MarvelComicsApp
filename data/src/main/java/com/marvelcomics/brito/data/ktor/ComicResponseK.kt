package com.marvelcomics.brito.data.ktor

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class ComicResponseK {
    @SerialName(value = "id")
    var id = 0

    @SerialName(value = "title")
    var title: String? = null

    @SerialName(value = "description")
    var description: String? = null

    @SerialName(value = "thumbnail")
    var thumbnailResponse: ThumbnailResponseK? = null
}