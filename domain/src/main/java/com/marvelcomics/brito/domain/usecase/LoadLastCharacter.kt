package com.marvelcomics.brito.domain.usecase

import com.marvelcomics.brito.domain.exception.NetworkException
import com.marvelcomics.brito.domain.handleDomain
import com.marvelcomics.brito.domain.models.CharacterDomain
import com.marvelcomics.brito.domain.repository.MarvelRepository
import com.marvelcomics.brito.domain.toCoroutineResult
import kotlinx.coroutines.CoroutineDispatcher
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class LoadLastCharacter(
    private val marvelRepository: MarvelRepository,
    dispatcher: CoroutineDispatcher
) : CoroutineUseCase<Any, CharacterDomain>(dispatcher) {

    override suspend fun performAction(param: Any?): Result<CharacterDomain> {
        return marvelRepository.getLastCharacterName().handleDomain().toCoroutineResult()
    }
}
