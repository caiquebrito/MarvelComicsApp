package com.marvelcomics.brito.presentation.search

import com.marvelcomics.brito.presentation.flow.UIEffect

sealed class SearchUiEffect : UIEffect {
    object ShowError : SearchUiEffect()
}
