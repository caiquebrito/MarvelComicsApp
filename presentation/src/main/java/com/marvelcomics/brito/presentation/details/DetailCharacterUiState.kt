package com.marvelcomics.brito.presentation.details

import com.marvelcomics.brito.entity.ComicEntity
import com.marvelcomics.brito.entity.SeriesEntity
import com.marvelcomics.brito.presentation.flow.UIState

data class DetailCharacterUiState(
    val isIdle: Boolean = false,
    val showComicsLoading: Boolean = false,
    val listComics: List<ComicEntity>? = null,
    val showSeriesLoading: Boolean = false,
    val listSeries: List<SeriesEntity>? = null
) : UIState
