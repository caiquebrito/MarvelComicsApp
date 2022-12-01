package com.marvelcomics.brito.domain.repository

import com.marvelcomics.brito.entity.CharacterEntity
import com.marvelcomics.brito.entity.ComicEntity
import com.marvelcomics.brito.entity.SeriesEntity

interface MarvelRepository {
    suspend fun getCharacters(name: String): List<CharacterEntity>
    suspend fun getComics(characterId: Int): List<ComicEntity>
    suspend fun getSeries(characterId: Int): List<SeriesEntity>
    suspend fun loadCharacterById(id: Int): CharacterEntity
    suspend fun saveCharacter(character: CharacterEntity)
    suspend fun loadAllCharactersIds(): List<Int>
    suspend fun loadAllCharacters(): List<CharacterEntity>
}
