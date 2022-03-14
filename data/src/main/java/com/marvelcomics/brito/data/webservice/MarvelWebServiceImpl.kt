package com.marvelcomics.brito.data.webservice

import com.marvelcomics.brito.data.OkHttpClientFactory
import com.marvelcomics.brito.data.WebServiceFactory
import com.marvelcomics.brito.data.datasource.remote.response.CharacterResponse
import com.marvelcomics.brito.data.datasource.remote.response.ComicResponse
import com.marvelcomics.brito.data.datasource.remote.response.SeriesResponse
import com.marvelcomics.brito.data.datasource.remote.response.model.RemoteMarvelContainerResponse
import okhttp3.Interceptor

class MarvelWebServiceImpl(baseUrl: String, vararg interceptors: Interceptor) : MarvelWebService {

    private val okHttpClient = OkHttpClientFactory.createHttpClient(*interceptors)
    private val marvelWebService = WebServiceFactory.createWebService(okHttpClient, baseUrl)

    override suspend fun characters(name: String): RemoteMarvelContainerResponse<CharacterResponse> {
        return marvelWebService.characters(name)
    }

    override suspend fun comics(characterId: Int): RemoteMarvelContainerResponse<ComicResponse> {
        return marvelWebService.comics(characterId)
    }

    override suspend fun series(characterId: Int): RemoteMarvelContainerResponse<SeriesResponse> {
        return marvelWebService.series(characterId)
    }
}
