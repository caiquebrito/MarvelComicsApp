package com.marvelcomics.brito.domain.usecase

import com.marvelcomics.brito.domain.exception.EmptyInputException
import com.marvelcomics.brito.domain.repository.MarvelRepository
import com.marvelcomics.brito.domain.usecase.models.CoroutineUseCase
import com.marvelcomics.brito.domain.usecase.models.Result
import com.marvelcomics.brito.domain.usecase.models.resultFromNullable
import com.marvelcomics.brito.entity.CharacterEntity
import kotlinx.coroutines.CoroutineDispatcher

class SaveCharacterUseCase(
    private val marvelRepository: MarvelRepository,
    dispatcher: CoroutineDispatcher
) : CoroutineUseCase<CharacterEntity, Any>(dispatcher) {

    override suspend fun performAction(param: CharacterEntity?): Result<Any> {
        return param?.let {
            marvelRepository.saveCharacter(it).resultFromNullable()
        } ?: throw EmptyInputException()
    }
}
