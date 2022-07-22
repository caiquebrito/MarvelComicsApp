package com.marvelcomics.brito.domain.usecase

import com.marvelcomics.brito.domain.exception.EmptyInputException
import com.marvelcomics.brito.domain.handleDomain
import com.marvelcomics.brito.domain.models.SeriesDomain
import com.marvelcomics.brito.domain.repository.MarvelRepository
import com.marvelcomics.brito.domain.toCoroutineResult
import kotlinx.coroutines.CoroutineDispatcher

class Series(
    private val marvelRepository: MarvelRepository,
    dispatcher: CoroutineDispatcher
) : CoroutineUseCase<Int, List<SeriesDomain>>(dispatcher) {

    override suspend fun performAction(param: Int?): Result<List<SeriesDomain>> {
        return param?.let {
            marvelRepository.getSeries(it).handleDomain().toCoroutineResult()
        } ?: throw EmptyInputException()
    }
}
