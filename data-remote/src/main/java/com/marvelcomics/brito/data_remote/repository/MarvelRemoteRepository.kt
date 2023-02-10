package com.marvelcomics.brito.data_remote.repository

import com.marvelcomics.brito.data.remote.MarvelRemoteDataSource
import com.marvelcomics.brito.data_remote.api.MarvelAPI
import com.marvelcomics.brito.data_remote.datasource.response.fromResponseToEntity
import com.marvelcomics.brito.data_remote.getBodyOrThrow
import com.marvelcomics.brito.data_remote.handleApi
import com.marvelcomics.brito.data_remote.throwIfNull
import com.marvelcomics.brito.entity.CharacterEntity
import com.marvelcomics.brito.entity.ComicEntity
import com.marvelcomics.brito.entity.SeriesEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MarvelRemoteRepository(
    private val api: MarvelAPI,
) : MarvelRemoteDataSource {

    override suspend fun getCharactersByName(name: String): List<CharacterEntity> = handleApi {
        api.characters(name).getBodyOrThrow().fromResponseToEntity().throwIfNull()
    }

    override suspend fun getComicsById(characterId: Int): List<ComicEntity> = handleApi {
        api.comics(characterId).getBodyOrThrow().fromResponseToEntity().throwIfNull()
    }

    override fun getSeriesById(characterId: Int): Flow<List<SeriesEntity>> = flow {
        emit(
            api.series(characterId).getBodyOrThrow().fromResponseToEntity().throwIfNull()
        )
    }
}
