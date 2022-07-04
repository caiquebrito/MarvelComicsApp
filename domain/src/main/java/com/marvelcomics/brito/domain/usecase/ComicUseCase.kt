package com.marvelcomics.brito.domain.usecase

import com.marvelcomics.brito.domain.exception.NetworkException
import com.marvelcomics.brito.domain.models.ComicDomain
import com.marvelcomics.brito.domain.repository.MarvelRepository
import kotlinx.coroutines.CoroutineDispatcher
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class ComicUseCase(
    private val marvelRepository: MarvelRepository,
    dispatcher: CoroutineDispatcher
) : CoroutineUseCase<Int, List<ComicDomain>>(dispatcher) {

    override suspend fun performAction(param: Int?): Result<List<ComicDomain>> {
        if (param == null) {
            throw Exception("Empty Param Character")
        }
        return try {
            marvelRepository.getComics(param).let { Result.fromNullable(it) }
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
