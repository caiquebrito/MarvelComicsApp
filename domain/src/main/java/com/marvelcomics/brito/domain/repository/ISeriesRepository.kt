package com.marvelcomics.brito.domain.repository

import com.marvelcomics.brito.domain.entity.SeriesEntity

interface ISeriesRepository {
    suspend fun series(characterId: Int): List<SeriesEntity>
}