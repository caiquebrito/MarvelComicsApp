package com.marvelcomics.brito.data.repository.comics

import com.marvelcomics.brito.data.datasource.remote.mapper.ComicMapper
import com.marvelcomics.brito.data.okhttp.safeApiCall
import com.marvelcomics.brito.data.webservice.MarvelWebService
import com.marvelcomics.brito.domain.ResultWrapper
import com.marvelcomics.brito.domain.entity.ComicEntity
import com.marvelcomics.brito.domain.repository.IComicRepository
import com.marvelcomics.brito.infrastructure.exception.MarvelMapperException

class ComicRepository(
    private val webService: MarvelWebService,
    private val comicMapper: ComicMapper
) : IComicRepository {
    override suspend fun getComics(characterId: Int): ResultWrapper<List<ComicEntity>> {
        return safeApiCall {
            comicMapper.transform(webService.comics(characterId))
                ?: throw MarvelMapperException("Error mapping character", null)
        }
    }
}
