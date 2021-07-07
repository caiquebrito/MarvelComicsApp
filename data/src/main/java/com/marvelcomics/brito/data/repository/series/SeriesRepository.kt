package com.marvelcomics.brito.data.repository.series

import com.marvelcomics.brito.data.datasource.remote.mapper.SeriesMapper
import com.marvelcomics.brito.data.webservice.MarvelWebService
import com.marvelcomics.brito.domain.entity.SeriesEntity
import com.marvelcomics.brito.domain.repository.ISeriesRepository
import com.marvelcomics.brito.infrastructure.exception.MarvelMapperException

class SeriesRepository(
    private val webService: MarvelWebService,
    private val seriesMapper: SeriesMapper
) : ISeriesRepository {
    override suspend fun getSeries(characterId: Int): List<SeriesEntity> {
        return seriesMapper.transform(webService.series(characterId))
            ?: throw MarvelMapperException("Error mapping series", null)
    }
}
