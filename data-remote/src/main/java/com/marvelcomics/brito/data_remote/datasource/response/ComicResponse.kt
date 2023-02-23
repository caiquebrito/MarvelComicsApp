package com.marvelcomics.brito.data_remote.datasource.response

import com.google.gson.annotations.SerializedName
import com.marvelcomics.brito.data_remote.datasource.response.model.RemoteMarvelContainerResponse
import com.marvelcomics.brito.entity.ComicEntity

class ComicResponse {
    @SerializedName("id")
    var id = 0

    @SerializedName("title")
    var title: String? = null

    @SerializedName("description")
    var description: String? = null

    @SerializedName("thumbnail")
    var thumbnailResponse: ThumbnailResponse? = null
}

fun RemoteMarvelContainerResponse<ComicResponse>.fromResponseToEntity(): List<ComicEntity>? {
    return this.remoteMarvelDataResponse?.results?.map { comicResponse ->
        ComicEntity(
            comicResponse.id,
            comicResponse.title,
            comicResponse.description,
            comicResponse.thumbnailResponse?.fromResponseToEntity()
        )
    }
}
