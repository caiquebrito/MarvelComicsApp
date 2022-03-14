package com.marvelcomics.brito.domain.usecase

import com.marvelcomics.brito.domain.exception.NetworkException
import com.marvelcomics.brito.domain.repository.ICharacterRepository
import kotlinx.coroutines.flow.flow
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class CharacterUseCase(private val iCharacterRepository: ICharacterRepository) {

    suspend fun getCharacters(name: String) = flow {
        try {
            emit(iCharacterRepository.getCharacters(name).first())
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
