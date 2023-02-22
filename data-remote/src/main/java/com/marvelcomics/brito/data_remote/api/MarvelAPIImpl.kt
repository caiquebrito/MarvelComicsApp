package com.marvelcomics.brito.data_remote.api

import com.marvelcomics.brito.data_remote.datasource.response.CharacterResponse
import com.marvelcomics.brito.data_remote.datasource.response.ComicResponse
import com.marvelcomics.brito.data_remote.datasource.response.SeriesResponse
import com.marvelcomics.brito.data_remote.datasource.response.model.RemoteMarvelContainerResponse
import retrofit2.Response

class MarvelAPIImpl(private val marvelWebService: MarvelAPI) : MarvelAPI {

    override suspend fun characters(name: String): Response<RemoteMarvelContainerResponse<CharacterResponse>> {
        return marvelWebService.characters(name)
    }

    override suspend fun comics(characterId: Int): Response<RemoteMarvelContainerResponse<ComicResponse>> {
        return marvelWebService.comics(characterId)
    }

    override fun series(characterId: Int): Response<RemoteMarvelContainerResponse<SeriesResponse>> {
        return marvelWebService.series(characterId)
    }
}
