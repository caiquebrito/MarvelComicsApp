package com.marvelcomics.brito.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

abstract class CoroutineUseCase<in TParam, out TResult>(private val dispatcher: CoroutineDispatcher) {

    sealed class Result<out TResultModel> {
        companion object {
            fun <TResultModel> fromNullable(result: TResultModel?) = when (result) {
                null -> Failure()
                else -> Success(result)
            }
        }

        data class Success<out TResultModel>(val result: TResultModel) : Result<TResultModel>()
        data class Failure(val error: Throwable? = null) : Result<Nothing>()
    }

    suspend operator fun invoke(param: TParam? = null) = try {
        withContext(dispatcher) {
            performAction(param)
        }
    } catch (error: Throwable) {
        Result.Failure(error)
    }

    protected abstract suspend fun performAction(param: TParam?): Result<TResult>
}