package com.marvelcomics.brito.data_remote.datasource.response

import com.google.gson.annotations.SerializedName
import com.marvelcomics.brito.entity.ThumbnailEntity

class ThumbnailResponse {
    @SerializedName("path")
    var path: String? = null

    @SerializedName("extension")
    var extension: String? = null
}

fun ThumbnailResponse.fromResponseToEntity(): ThumbnailEntity {
    return ThumbnailEntity(this.path, this.extension)
}
