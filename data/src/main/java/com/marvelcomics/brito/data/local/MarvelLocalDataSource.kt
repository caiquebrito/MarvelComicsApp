package com.marvelcomics.brito.data.local

import com.marvelcomics.brito.domain.models.CharacterDomain

interface MarvelLocalDataSource {
    suspend fun getCharacterById(id: Int): CharacterDomain
    suspend fun saveCharacter(character: CharacterDomain)
    suspend fun getAllCharactersIds(): List<Int>
}
