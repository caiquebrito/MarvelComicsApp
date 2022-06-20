package com.marvelcomics.brito.data_remote.datasource.response

import com.google.gson.annotations.SerializedName

class ThumbnailResponse {
    @SerializedName("path")
    var path: String? = null
    @SerializedName("extension")
    var extension: String? = null
}
