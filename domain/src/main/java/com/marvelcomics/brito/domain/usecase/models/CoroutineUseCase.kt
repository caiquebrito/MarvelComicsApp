package com.marvelcomics.brito.domain.usecase.models

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

abstract class CoroutineUseCase<in TParam, out TResult>(private val dispatcher: CoroutineDispatcher) {

    suspend operator fun invoke(param: TParam? = null) = try {
        withContext(dispatcher) {
            performAction(param)
        }
    } catch (error: Throwable) {
        Result.Failure(error)
    }

    protected abstract suspend fun performAction(param: TParam?): Result<TResult>
}

fun <TResultModel> Result<TResultModel>.onSuccess(callback: (TResultModel) -> Unit):
    Result<TResultModel> {
    if (this is Result.Success) {
        callback.invoke(this.result)
    }
    return this
}

fun <TResultModel> Result<TResultModel>.onFailure(callback: (Throwable) -> Unit):
    Result<TResultModel> {
    if (this is Result.Failure) {
        callback.invoke(this.error ?: Exception())
    }
    return this
}

fun <TResultModel> TResultModel.resultFromNullable(): Result<TResultModel> {
    return Result.fromNullable(this)
}
