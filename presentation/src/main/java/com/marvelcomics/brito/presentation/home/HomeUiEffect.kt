package com.marvelcomics.brito.presentation.home

import com.marvelcomics.brito.presentation.flow.UIEffect

sealed class HomeUiEffect : UIEffect {
    object OpenSearchScreen : HomeUiEffect()
    object ShowEmptyCharacters : HomeUiEffect()
}
