package com.marvelcomics.brito.domain.usecase

import com.marvelcomics.brito.domain.repository.MarvelRepository
import com.marvelcomics.brito.entity.CharacterEntity
import kotlinx.coroutines.CoroutineDispatcher

class LoadAllCharactersUseCase(
    private val marvelRepository: MarvelRepository,
    dispatcher: CoroutineDispatcher
) : CoroutineUseCase<Any, List<CharacterEntity>>(dispatcher) {

    override suspend fun performAction(param: Any?): Result<List<CharacterEntity>> {
        return marvelRepository.loadAllCharacters().resultFromNullable()
            .onSuccess {
                if (it.isEmpty()) {
                    throw EmptyException()
                }
            }
    }
}

class EmptyException : Throwable()
