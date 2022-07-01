package com.marvelcomics.brito.data_remote.repository

import com.marvelcomics.brito.data_remote.datasource.mapper.CharacterMapper
import com.marvelcomics.brito.data_remote.datasource.mapper.ComicMapper
import com.marvelcomics.brito.data_remote.datasource.mapper.SeriesMapper
import com.marvelcomics.brito.data_remote.exception.MarvelMapperException
import com.marvelcomics.brito.data_remote.api.MarvelAPI
import com.marvelcomics.brito.domain.models.CharacterDomain
import com.marvelcomics.brito.domain.models.ComicDomain
import com.marvelcomics.brito.domain.models.SeriesDomain
import com.marvelcomics.brito.domain.repository.IMarvelRepository

class MarvelRepository(
    private val webService: MarvelAPI,
    private val characterMapper: CharacterMapper,
    private val comicMapper: ComicMapper,
    private val seriesMapper: SeriesMapper
) : IMarvelRepository {

    override suspend fun getCharacters(name: String): List<CharacterDomain> {
        return characterMapper.transform(webService.characters(name))
            ?: run { throw MarvelMapperException("Error mapping character", null) }
    }

    override suspend fun getComics(characterId: Int): List<ComicDomain> {
        return comicMapper.transform(webService.comics(characterId))
            ?: throw MarvelMapperException("Error mapping character", null)
    }

    override suspend fun getSeries(characterId: Int): List<SeriesDomain> {
        return seriesMapper.transform(webService.series(characterId))
            ?: throw MarvelMapperException("Error mapping series", null)
    }
}
