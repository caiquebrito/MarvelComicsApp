package com.marvelcomics.brito.presentation.search

import com.marvelcomics.brito.presentation.flow.UIEffect

sealed class SearchUiEffect : UIEffect {
    object BackToHome : SearchUiEffect()
    object ShowError : SearchUiEffect()
    object ShowAlreadyAddedError : SearchUiEffect()
}
