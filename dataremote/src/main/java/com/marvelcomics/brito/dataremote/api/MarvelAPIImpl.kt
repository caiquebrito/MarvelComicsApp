package com.marvelcomics.brito.dataremote.api

import com.marvelcomics.brito.dataremote.datasource.response.CharacterResponse
import com.marvelcomics.brito.dataremote.datasource.response.ComicResponse
import com.marvelcomics.brito.dataremote.datasource.response.SeriesResponse
import com.marvelcomics.brito.dataremote.datasource.response.model.RemoteMarvelContainerResponse
import retrofit2.Response

class MarvelAPIImpl(private val marvelWebService: MarvelAPI) : MarvelAPI {

    override suspend fun characters(name: String): Response<RemoteMarvelContainerResponse<CharacterResponse>> {
        return marvelWebService.characters(name)
    }

    override suspend fun comics(characterId: Int): Response<RemoteMarvelContainerResponse<ComicResponse>> {
        return marvelWebService.comics(characterId)
    }

    override suspend fun series(characterId: Int): Response<RemoteMarvelContainerResponse<SeriesResponse>> {
        return marvelWebService.series(characterId)
    }
}
