package com.marvelcomics.brito.data_remote.api

import com.marvelcomics.brito.data_remote.datasource.response.CharacterResponse
import com.marvelcomics.brito.data_remote.datasource.response.ComicResponse
import com.marvelcomics.brito.data_remote.datasource.response.SeriesResponse
import com.marvelcomics.brito.data_remote.datasource.response.model.RemoteMarvelContainerResponse
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
    fun series(@Path("characterId") characterId: Int): Response<RemoteMarvelContainerResponse<SeriesResponse>>
}
