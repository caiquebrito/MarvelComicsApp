package com.marvelcomics.brito.domain.repository

import com.marvelcomics.brito.domain.models.CharacterDomain

interface ICharacterRepository {
    suspend fun getCharacters(name: String): List<CharacterDomain>
}
