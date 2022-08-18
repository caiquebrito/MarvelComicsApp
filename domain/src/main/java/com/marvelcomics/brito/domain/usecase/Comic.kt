package com.marvelcomics.brito.domain.usecase

import com.marvelcomics.brito.domain.exception.EmptyInputException
import com.marvelcomics.brito.domain.models.ComicDomain
import com.marvelcomics.brito.domain.repository.MarvelRepository
import kotlinx.coroutines.CoroutineDispatcher

class Comic(
    private val marvelRepository: MarvelRepository,
    dispatcher: CoroutineDispatcher
) : CoroutineUseCase<Int, List<ComicDomain>>(dispatcher) {

    override suspend fun performAction(param: Int?): Result<List<ComicDomain>> {
        return param?.let {
            marvelRepository.getComics(it).resultFromNullable()
        } ?: throw EmptyInputException()
    }
}
