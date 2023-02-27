package com.marvelcomics.brito.domain.usecase

import com.marvelcomics.brito.domain.repository.MarvelRepository
import com.marvelcomics.brito.domain.usecase.models.CoroutineUseCase
import com.marvelcomics.brito.domain.usecase.models.Result
import com.marvelcomics.brito.domain.usecase.models.resultFromNullable
import kotlinx.coroutines.CoroutineDispatcher

class LoadAllCharactersIdsUseCase(
    private val marvelRepository: MarvelRepository,
    dispatcher: CoroutineDispatcher
) : CoroutineUseCase<Any, List<Int>>(dispatcher) {

    override suspend fun performAction(param: Any?): Result<List<Int>> {
        return marvelRepository.loadAllCharactersIds().resultFromNullable()
    }
}
