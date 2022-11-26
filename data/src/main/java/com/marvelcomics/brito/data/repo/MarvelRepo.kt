package com.marvelcomics.brito.data.repo

import com.marvelcomics.brito.data.local.MarvelLocalDataSource
import com.marvelcomics.brito.data.remote.MarvelRemoteDataSource
import com.marvelcomics.brito.domain.models.CharacterDomain
import com.marvelcomics.brito.domain.models.ComicDomain
import com.marvelcomics.brito.domain.models.SeriesDomain
import com.marvelcomics.brito.domain.repository.MarvelRepository

class MarvelRepo(
    private val remote: MarvelRemoteDataSource,
    private val local: MarvelLocalDataSource
) : MarvelRepository {

    override suspend fun getCharacters(name: String): List<CharacterDomain> {
        return remote.getCharactersByName(name)
    }

    override suspend fun getComics(characterId: Int): List<ComicDomain> {
        return remote.getComicsById(characterId)
    }

    override suspend fun getSeries(characterId: Int): List<SeriesDomain> {
        return remote.getSeriesById(characterId)
    }

    override suspend fun getCharacterById(id: Int): CharacterDomain {
        return local.getCharacterById(id)
    }

    override suspend fun saveCharacter(character: CharacterDomain) {
        local.saveCharacter(character)
    }

    override suspend fun getAllCharactersIds(): List<Int> {
        return local.getAllCharactersIds()
    }

    override suspend fun getAllCharacters(): List<CharacterDomain> {
        return local.getAllCharacters()
    }
}
