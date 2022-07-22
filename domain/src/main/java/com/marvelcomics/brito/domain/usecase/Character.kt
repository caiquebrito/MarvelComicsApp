package com.marvelcomics.brito.domain.usecase

import com.marvelcomics.brito.domain.exception.EmptyInputException
import com.marvelcomics.brito.domain.handleDomain
import com.marvelcomics.brito.domain.models.CharacterDomain
import com.marvelcomics.brito.domain.repository.MarvelRepository
import com.marvelcomics.brito.domain.toCoroutineResult
import kotlinx.coroutines.CoroutineDispatcher

class Character(
    private val marvelRepository: MarvelRepository,
    dispatcher: CoroutineDispatcher
) : CoroutineUseCase<String, CharacterDomain>(dispatcher) {

    override suspend fun performAction(param: String?): Result<CharacterDomain> {
        return param?.let {
            marvelRepository.getCharacters(it).handleDomain().first().toCoroutineResult()
        } ?: throw EmptyInputException()
    }
}