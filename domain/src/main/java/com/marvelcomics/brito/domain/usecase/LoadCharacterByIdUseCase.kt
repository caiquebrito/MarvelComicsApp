package com.marvelcomics.brito.domain.usecase

import com.marvelcomics.brito.domain.exception.EmptyInputException
import com.marvelcomics.brito.domain.repository.MarvelRepository
import com.marvelcomics.brito.entity.CharacterEntity
import kotlinx.coroutines.CoroutineDispatcher

class LoadCharacterByIdUseCase(
    private val marvelRepository: MarvelRepository,
    dispatcher: CoroutineDispatcher
) : CoroutineUseCase<Int, CharacterEntity>(dispatcher) {

    override suspend fun performAction(param: Int?): Result<CharacterEntity> {
        return param?.let {
            marvelRepository.loadCharacterById(it).resultFromNullable()
        } ?: throw EmptyInputException()
    }
}
