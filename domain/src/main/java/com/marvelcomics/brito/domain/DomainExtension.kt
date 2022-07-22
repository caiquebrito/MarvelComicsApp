package com.marvelcomics.brito.domain

import com.marvelcomics.brito.domain.exception.NetworkException
import com.marvelcomics.brito.domain.models.ResultWrapper
import com.marvelcomics.brito.domain.usecase.CoroutineUseCase

fun <T> ResultWrapper<T>.handleDomain(): T {
    return try {
        when (this) {
            is ResultWrapper.Success -> {
                this.data
            }
            is ResultWrapper.NetworkError -> {
                throw NetworkException()
            }
            is ResultWrapper.Error -> {
                throw Exception(this.message)
            }
        }
    } catch (exception: Exception) {
        throw exception
    }
}

fun <T> T.toCoroutineResult(): CoroutineUseCase.Result<T> {
    return CoroutineUseCase.Result.fromNullable(this)
}