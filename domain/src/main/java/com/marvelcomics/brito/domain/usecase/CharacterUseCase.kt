package com.marvelcomics.brito.domain.usecase

import com.marvelcomics.brito.domain.ResultWrapper
import com.marvelcomics.brito.domain.entity.CharacterEntity
import com.marvelcomics.brito.domain.repository.ICharacterRepository

class CharacterUseCase(private val iCharacterRepository: ICharacterRepository) {

    suspend fun getCharacters(name: String): ResultWrapper<CharacterEntity> {
        return iCharacterRepository.getCharacters(name)
    }
}