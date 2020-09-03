package com.marvelcomics.brito.data.repository.comics

import com.marvelcomics.brito.data.handler.ResourceModel
import com.marvelcomics.brito.domain.entity.ComicEntity

interface ComicRepository {
    suspend fun comics(characterId: Int): ResourceModel<List<ComicEntity>>
}