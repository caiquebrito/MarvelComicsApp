package com.marvelcomics.brito.data.datasource.remote.response

import com.google.gson.annotations.SerializedName

class ThumbnailResponse {
    @SerializedName("path")
    var path: String? = null
    @SerializedName("extension")
    var extension: String? = null
}
