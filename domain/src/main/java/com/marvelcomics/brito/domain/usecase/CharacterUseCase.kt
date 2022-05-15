package com.marvelcomics.brito.domain.usecase

import com.marvelcomics.brito.domain.exception.EmptyInputException
import com.marvelcomics.brito.domain.exception.NetworkException
import com.marvelcomics.brito.domain.models.CharacterDomain
import com.marvelcomics.brito.domain.repository.ICharacterRepository
import kotlinx.coroutines.CoroutineDispatcher
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class CharacterUseCase(
    private val iCharacterRepository: ICharacterRepository,
    dispatcher: CoroutineDispatcher
) : CoroutineUseCase<String, CharacterDomain>(dispatcher) {

    override suspend fun performAction(param: String?): Result<CharacterDomain> {
        if (param == null) {
            throw EmptyInputException()
        }
        return try {
            iCharacterRepository.getCharacters(param).first().let { Result.fromNullable(it) }
        } catch (exception: Exception) {
            when (exception) {
                is UnknownHostException, is SocketException, is SocketTimeoutException -> {
                    throw NetworkException()
                }
                else -> {
                    throw exception
                }
            }
        }
    }
}
