package com.marvelcomics.brito.dataremote.repository

import com.marvelcomics.brito.data.remote.MarvelRemoteDataSource
import com.marvelcomics.brito.dataremote.api.MarvelAPI
import com.marvelcomics.brito.dataremote.datasource.response.fromResponseToEntity
import com.marvelcomics.brito.dataremote.extensions.getBodyOrThrow
import com.marvelcomics.brito.dataremote.extensions.handleApi
import com.marvelcomics.brito.dataremote.extensions.handleFlowApi
import com.marvelcomics.brito.dataremote.extensions.throwIfNull
import com.marvelcomics.brito.entity.ComicEntity
import com.marvelcomics.brito.entity.SeriesEntity
import kotlinx.coroutines.flow.Flow

class
MarvelRemoteRepository(
    private val api: MarvelAPI
) : MarvelRemoteDataSource {

    override suspend fun getCharactersByName(name: String) = handleApi {
        api.characters(name).getBodyOrThrow().fromResponseToEntity().throwIfNull()
    }

    override suspend fun getComicsById(characterId: Int): List<ComicEntity> = handleApi {
        api.comics(characterId).getBodyOrThrow().fromResponseToEntity().throwIfNull()
    }

    override suspend fun getSeriesById(characterId: Int): Flow<List<SeriesEntity>> = handleFlowApi {
        api.series(characterId).getBodyOrThrow().fromResponseToEntity().throwIfNull()
    }
}
