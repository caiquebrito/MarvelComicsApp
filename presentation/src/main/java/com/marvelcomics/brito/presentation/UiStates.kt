package com.marvelcomics.brito.presentation

sealed class HomeState {
    object NetworkError : HomeState()
    object Loading : HomeState()
    object Empty : HomeState()
    object Idle : HomeState()
    class Success(val `data`: Any) : HomeState()
    class Error(val exception: Throwable) : HomeState()
}

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
