package com.marvelcomics.brito.presentation.home

import com.marvelcomics.brito.presentation.flow.UIEffect

sealed class HomeUiEffect : UIEffect {
    data class OpenSearchScreen(val ids: List<Int>?) : HomeUiEffect()
    object ShowError : HomeUiEffect()
}
