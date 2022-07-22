package com.marvelcomics.brito.domain.usecase

import com.marvelcomics.brito.domain.exception.EmptyInputException
import com.marvelcomics.brito.domain.exception.NetworkException
import com.marvelcomics.brito.domain.handleDomain
import com.marvelcomics.brito.domain.models.ComicDomain
import com.marvelcomics.brito.domain.repository.MarvelRepository
import com.marvelcomics.brito.domain.toCoroutineResult
import kotlinx.coroutines.CoroutineDispatcher
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class Comic(
    private val marvelRepository: MarvelRepository,
    dispatcher: CoroutineDispatcher
) : CoroutineUseCase<Int, List<ComicDomain>>(dispatcher) {

    override suspend fun performAction(param: Int?): Result<List<ComicDomain>> {
        return param?.let {
            marvelRepository.getComics(it).handleDomain().toCoroutineResult()
        } ?: throw EmptyInputException()
    }
}
