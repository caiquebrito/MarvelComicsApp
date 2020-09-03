package com.marvelcomics.brito.domain.usecase

import com.marvelcomics.brito.domain.entity.ComicEntity
import com.marvelcomics.brito.domain.repository.IComicRepository

class ComicUseCase(private val iComicRepository: IComicRepository) {

    suspend fun getcomics(characterId: Int): List<ComicEntity> {
        return iComicRepository.comics(characterId)
    }
}