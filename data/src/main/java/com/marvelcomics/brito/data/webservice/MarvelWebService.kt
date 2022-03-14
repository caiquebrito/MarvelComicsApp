package com.marvelcomics.brito.data.webservice

import com.marvelcomics.brito.data.datasource.remote.response.CharacterResponse
import com.marvelcomics.brito.data.datasource.remote.response.ComicResponse
import com.marvelcomics.brito.data.datasource.remote.response.SeriesResponse
import com.marvelcomics.brito.data.datasource.remote.response.model.RemoteMarvelContainerResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MarvelWebService {

    @GET("characters")
    suspend fun characters(@Query("name") name: String): RemoteMarvelContainerResponse<CharacterResponse>

    @GET("characters/{characterId}/comics")
    suspend fun comics(@Path("characterId") characterId: Int): RemoteMarvelContainerResponse<ComicResponse>

    @GET("characters/{characterId}/series")
    suspend fun series(@Path("characterId") characterId: Int): RemoteMarvelContainerResponse<SeriesResponse>

    companion object {
        const val BASE_URL = "https://gateway.marvel.com/v1/public/"
    }
}
