package com.marvelcomics.brito.domain.ktor

import com.marvelcomics.brito.domain.entity.ComicEntity

class ComicKUseCase(private val iComicRepository: IComicKRepository) {

    suspend fun getComics(characterId: Int): List<ComicEntity> {
        return iComicRepository.getComics(characterId)
    }
}
