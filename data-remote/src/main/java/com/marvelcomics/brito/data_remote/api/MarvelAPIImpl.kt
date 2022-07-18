package com.marvelcomics.brito.data_remote.api

import com.marvelcomics.brito.data_remote.OkHttpClientFactory
import com.marvelcomics.brito.data_remote.WebServiceFactory
import com.marvelcomics.brito.data_remote.datasource.response.CharacterResponse
import com.marvelcomics.brito.data_remote.datasource.response.ComicResponse
import com.marvelcomics.brito.data_remote.datasource.response.SeriesResponse
import com.marvelcomics.brito.data_remote.datasource.response.model.RemoteMarvelContainerResponse
import okhttp3.Interceptor
import retrofit2.Response

class MarvelAPIImpl(baseUrl: String, vararg interceptors: Interceptor) : MarvelAPI {

    private val okHttpClient = OkHttpClientFactory.createHttpClient(*interceptors)
    private val marvelWebService = WebServiceFactory.createWebService(okHttpClient, baseUrl)

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
