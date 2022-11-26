package com.marvelcomics.brito.domain.repository

import com.marvelcomics.brito.domain.models.CharacterDomain
import com.marvelcomics.brito.domain.models.ComicDomain
import com.marvelcomics.brito.domain.models.SeriesDomain

interface MarvelRepository {
    suspend fun getCharacters(name: String): List<CharacterDomain>
    suspend fun getComics(characterId: Int): List<ComicDomain>
    suspend fun getSeries(characterId: Int): List<SeriesDomain>
    suspend fun getCharacterById(id: Int): CharacterDomain
    suspend fun saveCharacter(character: CharacterDomain)
    suspend fun getAllCharactersIds(): List<Int>
    suspend fun getAllCharacters(): List<CharacterDomain>
}
