package com.marvelcomics.brito.data.datasource.remote.response

import com.google.gson.annotations.SerializedName

class SeriesResponse {
    @SerializedName("id")
    var id = 0
    @SerializedName("title")
    var title: String? = null
    @SerializedName("description")
    var description: String? = null
    @SerializedName("thumbnail")
    var thumbnailResponse: ThumbnailResponse? = null
}
