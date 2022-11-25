package com.marvelcomics.brito.domain.usecase

import com.marvelcomics.brito.domain.exception.EmptyInputException
import com.marvelcomics.brito.domain.repository.MarvelRepository
import kotlinx.coroutines.CoroutineDispatcher

class LoadCharacterByIdUseCase(
    private val marvelRepository: MarvelRepository,
    dispatcher: CoroutineDispatcher
) : CoroutineUseCase<Int, Any>(dispatcher) {

    override suspend fun performAction(param: Int?): Result<Any> {
        return param?.let {
            marvelRepository.getCharacterById(it).resultFromNullable()
        } ?: throw EmptyInputException()
    }
}
