package com.marvelcomics.brito.data.models

sealed class RemoteWrapper<out T> {
    data class Success<out T>(val data: T) : RemoteWrapper<T>()
    data class Error(val code: Int?, val message: String?) : RemoteWrapper<Nothing>()
    object NetworkError : RemoteWrapper<Nothing>()
}
