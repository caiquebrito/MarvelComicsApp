package com.marvelcomics.brito.domain.usecase

import com.marvelcomics.brito.domain.exception.EmptyInputException
import com.marvelcomics.brito.domain.repository.MarvelRepository
import com.marvelcomics.brito.entity.ComicEntity
import kotlinx.coroutines.CoroutineDispatcher

class LoadComicsUseCase(
    private val marvelRepository: MarvelRepository,
    dispatcher: CoroutineDispatcher
) : CoroutineUseCase<Int, List<ComicEntity>>(dispatcher) {

    override suspend fun performAction(param: Int?): Result<List<ComicEntity>> {
        return param?.let {
            marvelRepository.getComics(it).resultFromNullable()
        } ?: throw EmptyInputException()
    }
}
