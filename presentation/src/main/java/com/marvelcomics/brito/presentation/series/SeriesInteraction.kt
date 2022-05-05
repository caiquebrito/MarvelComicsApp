package com.marvelcomics.brito.presentation.series

sealed class SeriesInteraction {
    class LoadSeriesById(val id: Int) : SeriesInteraction()
}