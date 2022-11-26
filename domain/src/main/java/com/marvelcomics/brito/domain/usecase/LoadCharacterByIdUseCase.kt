package com.marvelcomics.brito.domain.usecase

import com.marvelcomics.brito.domain.exception.EmptyInputException
import com.marvelcomics.brito.domain.models.CharacterDomain
import com.marvelcomics.brito.domain.repository.MarvelRepository
import kotlinx.coroutines.CoroutineDispatcher

class LoadCharacterByIdUseCase(
    private val marvelRepository: MarvelRepository,
    dispatcher: CoroutineDispatcher
) : CoroutineUseCase<Int, CharacterDomain>(dispatcher) {

    override suspend fun performAction(param: Int?): Result<CharacterDomain> {
        return param?.let {
            marvelRepository.getCharacterById(it).resultFromNullable()
        } ?: throw EmptyInputException()
    }
}
