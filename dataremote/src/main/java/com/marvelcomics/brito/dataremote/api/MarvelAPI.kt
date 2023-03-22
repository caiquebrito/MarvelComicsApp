package com.marvelcomics.brito.dataremote.api

import com.marvelcomics.brito.dataremote.datasource.response.CharacterResponse
import com.marvelcomics.brito.dataremote.datasource.response.ComicResponse
import com.marvelcomics.brito.dataremote.datasource.response.SeriesResponse
import com.marvelcomics.brito.dataremote.datasource.response.model.RemoteMarvelContainerResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MarvelAPI {

    @GET("characters")
    suspend fun characters(@Query("name") name: String): Response<RemoteMarvelContainerResponse<CharacterResponse>>

    @GET("characters/{characterId}/comics")
    suspend fun comics(@Path("characterId") characterId: Int): Response<RemoteMarvelContainerResponse<ComicResponse>>

    @GET("characters/{characterId}/series")
    suspend fun series(@Path("characterId") characterId: Int): Response<RemoteMarvelContainerResponse<SeriesResponse>>
}
