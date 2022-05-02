package com.marvelcomics.brito.domain.usecase

import com.marvelcomics.brito.domain.entity.CharacterEntity
import com.marvelcomics.brito.domain.exception.NetworkException
import com.marvelcomics.brito.domain.repository.ICharacterRepository
import kotlinx.coroutines.CoroutineDispatcher
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class CharacterUseCase(
    private val iCharacterRepository: ICharacterRepository,
    dispatcher: CoroutineDispatcher
) : CoroutineUseCase<String, CharacterEntity>(dispatcher) {

    override suspend fun performAction(param: String?): Result<CharacterEntity> {
        if (param == null) {
            throw Exception("Empty Param Character")
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
