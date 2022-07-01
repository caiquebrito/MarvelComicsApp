package com.marvelcomics.brito.data

import com.marvelcomics.brito.domain.models.CharacterDomain
import com.marvelcomics.brito.domain.models.ComicDomain
import com.marvelcomics.brito.domain.models.SeriesDomain

interface MarvelRemoteDataSource {
    suspend fun getCharacters(name: String): List<CharacterDomain>
    suspend fun getComics(characterId: Int): List<ComicDomain>
    suspend fun getSeries(characterId: Int): List<SeriesDomain>
}