package com.marvelcomics.brito.domain.repository

import com.marvelcomics.brito.domain.models.ComicDomain

interface IComicRepository {
    suspend fun getComics(characterId: Int): List<ComicDomain>
}
