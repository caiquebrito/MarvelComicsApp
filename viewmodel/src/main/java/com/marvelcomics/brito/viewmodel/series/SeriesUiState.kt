package com.marvelcomics.brito.viewmodel.series

import com.marvelcomics.brito.domain.entity.SeriesEntity

sealed class SeriesUiState {
    class Success(val list: List<SeriesEntity>) : SeriesUiState()
    object Loading : SeriesUiState()
    class Error(val exception: Exception) : SeriesUiState()
}