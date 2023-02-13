package com.marvelcomics.brito.presentation.details

import com.marvelcomics.brito.presentation.flow.UIEffect

sealed class DetailCharacterUiEffect : UIEffect {
    object ShowComicsError : DetailCharacterUiEffect()
    object ShowSeriesError : DetailCharacterUiEffect()
}
