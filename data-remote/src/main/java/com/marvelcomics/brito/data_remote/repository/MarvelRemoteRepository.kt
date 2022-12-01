package com.marvelcomics.brito.data_remote.repository

import com.marvelcomics.brito.data.remote.MarvelRemoteDataSource
import com.marvelcomics.brito.data_remote.api.MarvelAPI
import com.marvelcomics.brito.data_remote.datasource.mapper.CharacterMapper
import com.marvelcomics.brito.data_remote.datasource.mapper.ComicMapper
import com.marvelcomics.brito.data_remote.datasource.mapper.SeriesMapper
import com.marvelcomics.brito.data_remote.getBodyOrThrow
import com.marvelcomics.brito.data_remote.handleApi
import com.marvelcomics.brito.entity.CharacterEntity
import com.marvelcomics.brito.entity.ComicEntity
import com.marvelcomics.brito.entity.SeriesEntity

class MarvelRemoteRepository(
    private val api: MarvelAPI,
    private val characterMapper: CharacterMapper,
    private val comicMapper: ComicMapper,
    private val seriesMapper: SeriesMapper
) : MarvelRemoteDataSource {

    override suspend fun getCharactersByName(name: String): List<CharacterEntity> {
        return handleApi {
            characterMapper.transform(api.characters(name).getBodyOrThrow())
        }
    }

    override suspend fun getComicsById(characterId: Int): List<ComicEntity> {
        return handleApi {
            comicMapper.transform(api.comics(characterId).getBodyOrThrow())
        }
    }

    override suspend fun getSeriesById(characterId: Int): List<SeriesEntity> {
        return handleApi {
            seriesMapper.transform(api.series(characterId).getBodyOrThrow())
        }
    }
}
