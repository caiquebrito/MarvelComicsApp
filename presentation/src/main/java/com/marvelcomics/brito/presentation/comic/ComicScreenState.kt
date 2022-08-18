package com.marvelcomics.brito.presentation.comic

sealed class ComicScreenState {
    object NetworkError : ComicScreenState()
    object Loading : ComicScreenState()
    object Empty : ComicScreenState()
    object Idle : ComicScreenState()
    class Success(val data: Any) : ComicScreenState()
    class Error(val exception: Throwable) : ComicScreenState()
}
