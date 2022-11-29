package com.marvelcomics.brito.domain.usecase

import com.marvelcomics.brito.domain.exception.EmptyInputException
import com.marvelcomics.brito.domain.models.CharacterDomain
import com.marvelcomics.brito.domain.repository.MarvelRepository
import kotlinx.coroutines.CoroutineDispatcher

class LoadCharacterUseCase(
    private val marvelRepository: MarvelRepository,
    dispatcher: CoroutineDispatcher
) : CoroutineUseCase<String, List<CharacterDomain>>(dispatcher) {

    override suspend fun performAction(param: String?): Result<List<CharacterDomain>> {
        return param?.let { param ->
            marvelRepository.getCharacters(param).resultFromNullable()
                .onSuccess {
                    if (it.isEmpty()) {
                        throw EmptyException()
                    }
                }
        } ?: throw EmptyInputException()
    }
}
