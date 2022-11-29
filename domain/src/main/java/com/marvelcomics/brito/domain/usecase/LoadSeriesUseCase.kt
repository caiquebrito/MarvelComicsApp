package com.marvelcomics.brito.domain.usecase

import com.marvelcomics.brito.domain.exception.EmptyInputException
import com.marvelcomics.brito.domain.repository.MarvelRepository
import com.marvelcomics.brito.entity.SeriesEntity
import kotlinx.coroutines.CoroutineDispatcher

class LoadSeriesUseCase(
    private val marvelRepository: MarvelRepository,
    dispatcher: CoroutineDispatcher
) : CoroutineUseCase<Int, List<SeriesEntity>>(dispatcher) {

    override suspend fun performAction(param: Int?): Result<List<SeriesEntity>> {
        return param?.let {
            marvelRepository.getSeries(it).resultFromNullable()
        } ?: throw EmptyInputException()
    }
}
