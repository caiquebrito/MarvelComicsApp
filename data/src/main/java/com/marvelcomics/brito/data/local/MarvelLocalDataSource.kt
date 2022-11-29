package com.marvelcomics.brito.data.local

import com.marvelcomics.brito.entity.CharacterEntity

interface MarvelLocalDataSource {
    suspend fun loadCharacterById(id: Int): CharacterEntity
    suspend fun saveCharacter(character: CharacterEntity)
    suspend fun loadAllCharactersIds(): List<Int>
    suspend fun loadAllCharacters(): List<CharacterEntity>
}
