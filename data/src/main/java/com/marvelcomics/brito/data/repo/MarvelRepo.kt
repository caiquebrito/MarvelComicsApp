package com.marvelcomics.brito.data.repo

import com.marvelcomics.brito.data.local.MarvelLocalDataSource
import com.marvelcomics.brito.data.remote.MarvelRemoteDataSource
import com.marvelcomics.brito.domain.repository.MarvelRepository
import com.marvelcomics.brito.entity.CharacterEntity
import com.marvelcomics.brito.entity.ComicEntity
import com.marvelcomics.brito.entity.SeriesEntity

class MarvelRepo(
    private val remote: MarvelRemoteDataSource,
    private val local: MarvelLocalDataSource
) : MarvelRepository {

    override suspend fun getCharacters(name: String): List<CharacterEntity> {
        return remote.getCharactersByName(name)
    }

    override suspend fun getComics(characterId: Int): List<ComicEntity> {
        return remote.getComicsById(characterId)
    }

    override suspend fun getSeries(characterId: Int): List<SeriesEntity> {
        return remote.getSeriesById(characterId)
    }

    override suspend fun loadCharacterById(id: Int): CharacterEntity {
        return local.loadCharacterById(id)
    }

    override suspend fun saveCharacter(character: CharacterEntity) {
        local.saveCharacter(character)
    }

    override suspend fun loadAllCharactersIds(): List<Int> {
        return local.loadAllCharactersIds()
    }

    override suspend fun loadAllCharacters(): List<CharacterEntity> {
        return local.loadAllCharacters()
    }
}
