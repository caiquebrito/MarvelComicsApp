package com.marvelcomics.brito.domain.usecase.models

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn

abstract class FlowUseCase<in TParam, out TResult>(private val dispatcher: CoroutineDispatcher) {
    suspend operator fun invoke(param: TParam? = null) =
        performAction(param)
            .catch { exception ->
                emit(Result.Failure(exception))
            }
            .flowOn(dispatcher)

    protected abstract suspend fun performAction(param: TParam?): Flow<Result<TResult>>
}
