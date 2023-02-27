package com.marvelcomics.brito.domain.usecase

import com.marvelcomics.brito.domain.exception.EmptyInputException
import com.marvelcomics.brito.domain.repository.MarvelRepository
import com.marvelcomics.brito.domain.usecase.models.CoroutineUseCase
import com.marvelcomics.brito.domain.usecase.models.Result
import com.marvelcomics.brito.domain.usecase.models.resultFromNullable
import com.marvelcomics.brito.entity.CharacterEntity
import kotlinx.coroutines.CoroutineDispatcher

class LoadCharacterUseCase(
    private val marvelRepository: MarvelRepository,
    dispatcher: CoroutineDispatcher
) : CoroutineUseCase<String, List<CharacterEntity>>(dispatcher) {

    override suspend fun performAction(param: String?): Result<List<CharacterEntity>> {
        return param?.let {
            marvelRepository.getCharacters(it).resultFromNullable()
        } ?: throw EmptyInputException()
    }
}
