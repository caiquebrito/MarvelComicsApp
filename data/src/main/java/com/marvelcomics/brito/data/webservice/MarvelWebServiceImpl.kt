package com.marvelcomics.brito.data.webservice

import com.marvelcomics.brito.data.OkHttpClientFactory
import com.marvelcomics.brito.data.WebServiceFactory
import com.marvelcomics.brito.data.datasource.remote.response.CharacterResponse
import com.marvelcomics.brito.data.datasource.remote.response.ComicResponse
import com.marvelcomics.brito.data.datasource.remote.response.SeriesResponse
import com.marvelcomics.brito.data.datasource.remote.response.model.RemoteMarvelContainer
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

class MarvelWebServiceImpl(baseUrl: String, vararg interceptors: Interceptor) : MarvelWebService {

    private val okHttpClient = OkHttpClientFactory.createHttpClient(*interceptors)
    private val marvelWebService = WebServiceFactory.createWebService(okHttpClient, baseUrl)

    override suspend fun characters(name: String): RemoteMarvelContainer<CharacterResponse> {
        return marvelWebService.characters(name)
    }

    override suspend fun comics(characterId: Int): RemoteMarvelContainer<ComicResponse> {
        return marvelWebService.comics(characterId)
    }

    override suspend fun series(characterId: Int): RemoteMarvelContainer<SeriesResponse> {
        return marvelWebService.series(characterId)
    }
}