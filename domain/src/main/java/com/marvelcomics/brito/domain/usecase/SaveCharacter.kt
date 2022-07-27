package com.marvelcomics.brito.domain.usecase

import com.marvelcomics.brito.domain.exception.EmptyInputException
import com.marvelcomics.brito.domain.models.CharacterDomain
import com.marvelcomics.brito.domain.repository.MarvelRepository
import kotlinx.coroutines.CoroutineDispatcher

class SaveCharacter(
    private val marvelRepository: MarvelRepository,
    dispatcher: CoroutineDispatcher
) : CoroutineUseCase<CharacterDomain, Any>(dispatcher) {

    override suspend fun performAction(param: CharacterDomain?): Result<Any> {
        return param?.let {
            marvelRepository.setLastCharacterName(it).resultFromNullable()
        } ?: throw EmptyInputException()
    }
}
