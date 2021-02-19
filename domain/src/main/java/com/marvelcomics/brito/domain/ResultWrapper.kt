package com.marvelcomics.brito.domain

sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T) : ResultWrapper<T>()
    data class Failure(val code: Int, val error: Throwable) : ResultWrapper<Nothing>()
    object NetworkError : ResultWrapper<Nothing>()
}