package com.marvelcomics.brito.presentation.series

sealed class SeriesScreenState {
    object NetworkError : SeriesScreenState()
    object Loading : SeriesScreenState()
    object Empty : SeriesScreenState()
    object Idle : SeriesScreenState()
    class Success(val `data`: Any) : SeriesScreenState()
    class Error(val exception: Throwable) : SeriesScreenState()
}