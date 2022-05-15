package com.marvelcomics.brito.domain.repository

import com.marvelcomics.brito.domain.models.SeriesDomain

interface ISeriesRepository {
    suspend fun getSeries(characterId: Int): List<SeriesDomain>
}
