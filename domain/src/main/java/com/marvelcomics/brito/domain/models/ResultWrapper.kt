package com.marvelcomics.brito.domain.models

sealed class ResultWrapper<out T> {
    data class Success<out T>(val data: T) : ResultWrapper<T>()
    data class Error(val code: Int?, val message: String?) : ResultWrapper<Nothing>()
    object NetworkError : ResultWrapper<Nothing>()
}
