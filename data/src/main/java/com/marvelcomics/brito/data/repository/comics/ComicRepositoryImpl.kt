package com.marvelcomics.brito.data.repository.comics

import com.marvelcomics.brito.data.datasource.remote.mapper.ComicMapper
import com.marvelcomics.brito.data.entity.ComicEntity
import com.marvelcomics.brito.data.handler.ResourceModel
import com.marvelcomics.brito.data.repository.BaseRepositoryImpl
import com.marvelcomics.brito.data.webservice.MarvelWebService
import com.marvelcomics.brito.infrastructure.exception.MarvelMapperException

class ComicRepositoryImpl(
    private val webService: MarvelWebService,
    private val comicMapper: ComicMapper
) : ComicRepository, BaseRepositoryImpl() {
    override suspend fun comics(characterId: Int): ResourceModel<List<ComicEntity>> {
        return try {
            val listEntity = comicMapper.transform(webService.comics(characterId))
            listEntity?.let {
                responseHandler.handleSuccess(it)
            } ?: let {
                throw MarvelMapperException("Error mapping comics", null)
            }
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }
}