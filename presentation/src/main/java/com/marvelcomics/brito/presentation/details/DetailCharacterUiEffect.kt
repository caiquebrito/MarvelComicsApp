package com.marvelcomics.brito.presentation.details

import com.marvelcomics.brito.entity.ComicEntity
import com.marvelcomics.brito.entity.SeriesEntity
import com.marvelcomics.brito.presentation.flow.UIEffect

sealed class DetailCharacterUiEffect : UIEffect {
    data class ShowComics(val list: List<ComicEntity>) : DetailCharacterUiEffect()
    object ShowComicsError : DetailCharacterUiEffect()
    data class ShowSeries(val list: List<SeriesEntity>) : DetailCharacterUiEffect()
    object ShowSeriesError : DetailCharacterUiEffect()
}
