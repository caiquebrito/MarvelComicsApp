package com.marvelcomics.brito.domain.usecase

import com.marvelcomics.brito.domain.exception.NetworkException
import com.marvelcomics.brito.domain.repository.IComicRepository
import kotlinx.coroutines.flow.flow
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class ComicUseCase(private val iComicRepository: IComicRepository) {

    suspend fun getComics(characterId: Int) = flow {
        try {
            emit(iComicRepository.getComics(characterId))
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
