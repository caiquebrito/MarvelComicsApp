package com.marvelcomics.brito.domain.usecase

import com.marvelcomics.brito.domain.exception.EmptyInputException
import com.marvelcomics.brito.domain.exception.NetworkException
import com.marvelcomics.brito.domain.models.CharacterDomain
import com.marvelcomics.brito.domain.repository.MarvelRepository
import com.marvelcomics.brito.domain.toCoroutineResult
import kotlinx.coroutines.CoroutineDispatcher
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class SaveCharacter(
    private val marvelRepository: MarvelRepository,
    dispatcher: CoroutineDispatcher
) : CoroutineUseCase<CharacterDomain, Any>(dispatcher) {

    override suspend fun performAction(param: CharacterDomain?): Result<Any> {
        return param?.let {
            marvelRepository.setLastCharacterName(it).toCoroutineResult()
        } ?: throw EmptyInputException()
    }
}
