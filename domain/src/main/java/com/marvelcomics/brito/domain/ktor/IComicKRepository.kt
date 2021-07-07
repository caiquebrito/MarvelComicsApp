package com.marvelcomics.brito.domain.ktor

import com.marvelcomics.brito.domain.entity.ComicEntity

interface IComicKRepository {
    suspend fun getComics(characterId: Int): List<ComicEntity>
}
