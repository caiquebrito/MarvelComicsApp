package com.marvelcomics.brito.data.repository.series

import com.marvelcomics.brito.data.datasource.remote.mapper.SeriesMapper
import com.marvelcomics.brito.data.entity.SeriesEntity
import com.marvelcomics.brito.data.handler.ResourceModel
import com.marvelcomics.brito.data.repository.BaseRepositoryImpl
import com.marvelcomics.brito.data.webservice.MarvelWebService
import com.marvelcomics.brito.infrastructure.exception.MarvelMapperException

class SeriesRepositoryImpl(
    private val webService: MarvelWebService,
    private val seriesMapper: SeriesMapper
) : SeriesRepository,
    BaseRepositoryImpl() {
    override suspend fun series(characterId: Int): ResourceModel<List<SeriesEntity>> {
        return try {
            val listEntity = seriesMapper.transform(webService.series(characterId))
            listEntity?.let {
                responseHandler.handleSuccess(it)
            } ?: let {
                throw MarvelMapperException("Error mapping series", null)
            }
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }
}