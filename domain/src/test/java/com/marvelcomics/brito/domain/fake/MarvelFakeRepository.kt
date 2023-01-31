package com.marvelcomics.brito.domain.fake

import com.marvelcomics.brito.domain.repository.MarvelRepository
import com.marvelcomics.brito.entity.CharacterEntity
import com.marvelcomics.brito.entity.ComicEntity
import com.marvelcomics.brito.entity.SeriesEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class MarvelFakeRepository : MarvelRepository {

    override suspend fun getCharacters(name: String): List<CharacterEntity> {
        return listOf(CharacterEntity(1, "Character", "Description", null))
    }

    override suspend fun getComics(characterId: Int): List<ComicEntity> {
        return listOf(ComicEntity(1, "Title", "Description"))
    }

    override fun getSeries(characterId: Int): Flow<List<SeriesEntity>> {
        return flowOf(listOf(SeriesEntity(1, "Title", "Description", null)))
    }

    override suspend fun loadCharacterById(id: Int): CharacterEntity {
        TODO("Not yet implemented")
    }

    override suspend fun saveCharacter(character: CharacterEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun loadAllCharactersIds(): List<Int> {
        TODO("Not yet implemented")
    }

    override suspend fun loadAllCharacters(): List<CharacterEntity> {
        TODO("Not yet implemented")
    }
}