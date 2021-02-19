package com.marvelcomics.brito.domain.usecase

import com.marvelcomics.brito.domain.ResultWrapper
import com.marvelcomics.brito.domain.entity.SeriesEntity
import com.marvelcomics.brito.domain.repository.ISeriesRepository

class SeriesUseCase(private val iSeriesRepository: ISeriesRepository) {

    suspend fun getSeries(characterId: Int): ResultWrapper<List<SeriesEntity>> {
        return iSeriesRepository.getSeries(characterId)
    }
}
