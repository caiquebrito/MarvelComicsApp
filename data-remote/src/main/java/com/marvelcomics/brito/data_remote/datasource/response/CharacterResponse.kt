package com.marvelcomics.brito.data_remote.datasource.response

import com.google.gson.annotations.SerializedName
import com.marvelcomics.brito.data_remote.datasource.response.model.RemoteMarvelContainerResponse
import com.marvelcomics.brito.entity.CharacterEntity

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

fun RemoteMarvelContainerResponse<CharacterResponse>.fromResponseToEntity(): List<CharacterEntity>? {
    return this.remoteMarvelDataResponse?.results?.map { characterResponse ->
        CharacterEntity(
            characterResponse.id,
            characterResponse.name,
            characterResponse.description,
            characterResponse.thumbnailResponse?.fromResponseToEntity()
        )
    }
}
