package com.marvelcomics.brito.viewmodel

sealed class GlobalUiState {
    object NetworkError
    object Loading
    object Empty
}

sealed class CharacterUiState {
    class Success(val `data`: Any)
    class Error(val exception: Throwable)
}

sealed class ComicUiState {
    class Success(val `data`: Any)
    class Error(val exception: Throwable)
}

sealed class SeriesUiState {
    class Success(val `data`: Any)
    class Error(val exception: Throwable)
}