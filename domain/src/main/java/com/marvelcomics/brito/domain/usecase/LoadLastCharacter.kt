package com.marvelcomics.brito.domain.usecase

import com.marvelcomics.brito.domain.models.CharacterDomain
import com.marvelcomics.brito.domain.repository.MarvelRepository
import kotlinx.coroutines.CoroutineDispatcher

class LoadLastCharacter(
    private val marvelRepository: MarvelRepository,
    dispatcher: CoroutineDispatcher
) : CoroutineUseCase<Any, CharacterDomain>(dispatcher) {

    override suspend fun performAction(param: Any?): Result<CharacterDomain> {
        return marvelRepository.getLastCharacterName().resultFromNullable()
    }
}
