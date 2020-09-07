package com.marvelcomics.brito.domain.repository

import com.marvelcomics.brito.domain.entity.ComicEntity

interface IComicRepository {
    suspend fun getComics(characterId: Int): List<ComicEntity>
}