package com.marvelcomics.brito.domain.usecase

import com.marvelcomics.brito.domain.entity.ComicEntity
import com.marvelcomics.brito.domain.repository.IComicRepository

class ComicUseCase(private val iComicRepository: IComicRepository) {

    suspend fun getComics(characterId: Int): List<ComicEntity> {
        return iComicRepository.getComics(characterId)
    }
}