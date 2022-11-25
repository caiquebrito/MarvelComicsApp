package com.marvelcomics.brito.data.remote

import com.marvelcomics.brito.domain.models.CharacterDomain
import com.marvelcomics.brito.domain.models.ComicDomain
import com.marvelcomics.brito.domain.models.SeriesDomain

interface MarvelRemoteDataSource {
    suspend fun getCharactersByName(name: String): List<CharacterDomain>
    suspend fun getComicsById(characterId: Int): List<ComicDomain>
    suspend fun getSeriesById(characterId: Int): List<SeriesDomain>
}
