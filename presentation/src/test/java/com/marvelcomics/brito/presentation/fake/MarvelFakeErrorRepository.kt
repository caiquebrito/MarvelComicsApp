package com.marvelcomics.brito.presentation.fake

import com.marvelcomics.brito.domain.repository.MarvelRepository
import com.marvelcomics.brito.entity.CharacterEntity
import com.marvelcomics.brito.entity.ComicEntity
import com.marvelcomics.brito.entity.SeriesEntity
import kotlinx.coroutines.flow.Flow

class MarvelFakeErrorRepository : MarvelRepository {

    override suspend fun getCharacters(name: String): List<CharacterEntity> {
        throw Throwable()
    }

    override suspend fun getComics(characterId: Int): List<ComicEntity> {
        throw Throwable()
    }

    override fun getSeries(characterId: Int): Flow<List<SeriesEntity>> {
        throw Throwable()
    }

    override suspend fun loadCharacterById(id: Int): CharacterEntity {
        throw Throwable()
    }

    override suspend fun saveCharacter(character: CharacterEntity) {
        throw Throwable()
    }

    override suspend fun loadAllCharactersIds(): List<Int> {
        throw Throwable()
    }

    override suspend fun loadAllCharacters(): List<CharacterEntity> {
        throw Throwable()
    }
}
