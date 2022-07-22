package com.marvelcomics.brito.data.remote

import com.marvelcomics.brito.domain.models.CharacterDomain
import com.marvelcomics.brito.domain.models.ComicDomain
import com.marvelcomics.brito.domain.models.ResultWrapper
import com.marvelcomics.brito.domain.models.SeriesDomain

interface MarvelRemoteDataSource {
    suspend fun getCharacters(name: String): ResultWrapper<List<CharacterDomain>>
    suspend fun getComics(characterId: Int): ResultWrapper<List<ComicDomain>>
    suspend fun getSeries(characterId: Int): ResultWrapper<List<SeriesDomain>>
}
