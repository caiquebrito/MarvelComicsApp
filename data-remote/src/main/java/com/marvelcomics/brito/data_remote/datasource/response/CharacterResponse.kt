package com.marvelcomics.brito.data_remote.datasource.response

import com.google.gson.annotations.SerializedName

class CharacterResponse {
    @SerializedName("id")
    var id = 0
    @SerializedName("name")
    var name: String? = null
    @SerializedName("description")
    var description: String? = null
    @SerializedName("modified")
    var modified: String? = null
    @SerializedName("thumbnail")
    var thumbnailResponse: ThumbnailResponse? = null
}
