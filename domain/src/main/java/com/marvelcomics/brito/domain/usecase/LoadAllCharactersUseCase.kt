package com.marvelcomics.brito.domain.usecase

import com.marvelcomics.brito.domain.models.CharacterDomain
import com.marvelcomics.brito.domain.repository.MarvelRepository
import kotlinx.coroutines.CoroutineDispatcher

class LoadAllCharactersUseCase(
    private val marvelRepository: MarvelRepository,
    dispatcher: CoroutineDispatcher
) : CoroutineUseCase<Any, List<CharacterDomain>>(dispatcher) {

    override suspend fun performAction(param: Any?): Result<List<CharacterDomain>> {
        return marvelRepository.getAllCharacters().resultFromNullable()
    }
}
