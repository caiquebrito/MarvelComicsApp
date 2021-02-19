package com.marvelcomics.brito.domain.repository

import com.marvelcomics.brito.domain.ResultWrapper
import com.marvelcomics.brito.domain.entity.CharacterEntity

interface ICharacterRepository {
    suspend fun getCharacters(name: String): ResultWrapper<CharacterEntity>
}