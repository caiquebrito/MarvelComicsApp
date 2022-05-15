package com.marvelcomics.brito.domain.usecase

import com.marvelcomics.brito.domain.exception.NetworkException
import com.marvelcomics.brito.domain.models.SeriesDomain
import com.marvelcomics.brito.domain.repository.ISeriesRepository
import kotlinx.coroutines.CoroutineDispatcher
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class SeriesUseCase(
    private val iSeriesRepository: ISeriesRepository,
    dispatcher: CoroutineDispatcher
) : CoroutineUseCase<Int, List<SeriesDomain>>(dispatcher) {

    override suspend fun performAction(param: Int?): Result<List<SeriesDomain>> {
        if (param == null) {
            throw Exception("Empty Param Character")
        }
        return try {
            iSeriesRepository.getSeries(param).let { Result.fromNullable(it) }
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
