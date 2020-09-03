package com.marvelcomics.brito.viewmodel.comic

import com.marvelcomics.brito.domain.entity.ComicEntity

sealed class ComicUiState {
    class Success(val list: List<ComicEntity>) : ComicUiState()
    object Loading : ComicUiState()
    class Error(val exception: Exception) : ComicUiState()
}