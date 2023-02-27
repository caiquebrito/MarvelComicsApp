package com.marvelcomics.brito.dataremote.datasource.response

import com.google.gson.annotations.SerializedName
import com.marvelcomics.brito.dataremote.datasource.response.model.RemoteMarvelContainerResponse
import com.marvelcomics.brito.entity.SeriesEntity

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

fun RemoteMarvelContainerResponse<SeriesResponse>.fromResponseToEntity(): List<SeriesEntity>? {
    return this.remoteMarvelDataResponse?.results?.map { seriesResponse ->
        SeriesEntity(
            seriesResponse.id,
            seriesResponse.title,
            seriesResponse.description,
            seriesResponse.thumbnailResponse?.fromResponseToEntity()
        )
    }
}
