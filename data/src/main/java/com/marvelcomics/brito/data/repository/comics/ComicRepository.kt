package com.marvelcomics.brito.data.repository.comics

import com.marvelcomics.brito.data.datasource.remote.mapper.ComicMapper
import com.marvelcomics.brito.data.webservice.MarvelWebService
import com.marvelcomics.brito.domain.entity.ComicEntity
import com.marvelcomics.brito.domain.repository.IComicRepository
import com.marvelcomics.brito.infrastructure.exception.MarvelMapperException

class ComicRepository(
    private val webService: MarvelWebService,
    private val comicMapper: ComicMapper
) : IComicRepository {
    override suspend fun getComics(characterId: Int): List<ComicEntity> {
        val listEntity = comicMapper.transform(webService.comics(characterId))
        return listEntity ?: let {
            throw MarvelMapperException("Error mapping comics", null)
        }
    }
}