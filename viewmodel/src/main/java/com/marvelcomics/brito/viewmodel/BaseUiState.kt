package com.marvelcomics.brito.viewmodel

open class BaseUiState<out T : Any> {
    class Success<out T : Any>(val `object`: T) : BaseUiState<T>()
    object Loading : BaseUiState<Nothing>()
    class Error(val exception: Throwable) : BaseUiState<Nothing>()
    object Empty : BaseUiState<Nothing>()
    object NetworkError : BaseUiState<Nothing>()
}