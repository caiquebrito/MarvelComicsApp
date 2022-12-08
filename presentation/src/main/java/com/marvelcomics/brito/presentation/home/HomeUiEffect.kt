package com.marvelcomics.brito.presentation.home

import com.marvelcomics.brito.entity.CharacterEntity
import com.marvelcomics.brito.presentation.flow.UIEffect

sealed class HomeUiEffect : UIEffect {
    data class OpenSearchScreen(val ids: List<Int>?) : HomeUiEffect()
    data class OpenDetailScreen(val entity: CharacterEntity) : HomeUiEffect()
    object ShowError : HomeUiEffect()
}
