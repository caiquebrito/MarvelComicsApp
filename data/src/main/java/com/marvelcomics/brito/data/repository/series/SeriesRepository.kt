package com.marvelcomics.brito.data.repository.series

import com.marvelcomics.brito.data.datasource.remote.response.ComicResponse
import com.marvelcomics.brito.data.datasource.remote.response.SeriesResponse
import com.marvelcomics.brito.data.datasource.remote.response.model.RemoteMarvelContainer
import com.marvelcomics.brito.data.handler.ResourceModel

interface SeriesRepository {
    suspend fun series(characterId: Int): ResourceModel<SeriesResponse>
}