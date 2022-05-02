package com.marvelcomics.brito.presentation

sealed class ComicUiState {
    object NetworkError
    object Loading
    object Empty
    object Idle
    class Success(val `data`: Any)
    class Error(val exception: Throwable)
}

sealed class SeriesUiState {
    object NetworkError
    object Loading
    object Empty
    object Idle
    class Success(val `data`: Any)
    class Error(val exception: Throwable)
}
