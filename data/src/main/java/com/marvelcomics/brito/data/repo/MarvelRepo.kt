package com.marvelcomics.brito.data.repo

import com.marvelcomics.brito.data.local.MarvelLocalDataSource
import com.marvelcomics.brito.data.remote.MarvelRemoteDataSource
import com.marvelcomics.brito.domain.models.CharacterDomain
import com.marvelcomics.brito.domain.models.ComicDomain
import com.marvelcomics.brito.domain.models.ResultWrapper
import com.marvelcomics.brito.domain.models.SeriesDomain
import com.marvelcomics.brito.domain.repository.MarvelRepository

class MarvelRepo(
    private val remote: MarvelRemoteDataSource,
    private val local: MarvelLocalDataSource
) : MarvelRepository {

    override suspend fun getCharacters(name: String): ResultWrapper<List<CharacterDomain>> {
        return remote.getCharacters(name)
    }

    override suspend fun getComics(characterId: Int): ResultWrapper<List<ComicDomain>> {
        return remote.getComics(characterId)
    }

    override suspend fun getSeries(characterId: Int): ResultWrapper<List<SeriesDomain>> {
        return remote.getSeries(characterId)
    }

    override suspend fun getLastCharacterName(): ResultWrapper<CharacterDomain> {
        return local.getLastCharacter()
    }

    override suspend fun setLastCharacterName(characterDomain: CharacterDomain) {
        local.setLastCharacter(characterDomain)
    }
}
