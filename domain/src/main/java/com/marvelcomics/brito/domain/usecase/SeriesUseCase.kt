package com.marvelcomics.brito.domain.usecase

import com.marvelcomics.brito.domain.exception.NetworkException
import com.marvelcomics.brito.domain.repository.ISeriesRepository
import kotlinx.coroutines.flow.flow
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class SeriesUseCase(private val iSeriesRepository: ISeriesRepository) {

    suspend fun getSeries(characterId: Int) = flow {
        try {
            emit(iSeriesRepository.getSeries(characterId))
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
