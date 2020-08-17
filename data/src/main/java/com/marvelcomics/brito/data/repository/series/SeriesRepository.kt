package com.marvelcomics.brito.data.repository.series

import com.marvelcomics.brito.data.entity.SeriesEntity
import com.marvelcomics.brito.data.handler.ResourceModel

interface SeriesRepository {
    suspend fun series(characterId: Int): ResourceModel<List<SeriesEntity>>
}