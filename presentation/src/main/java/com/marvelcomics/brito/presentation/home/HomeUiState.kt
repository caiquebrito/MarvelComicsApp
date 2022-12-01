package com.marvelcomics.brito.presentation.home

import com.marvelcomics.brito.entity.CharacterEntity
import com.marvelcomics.brito.presentation.flow.UIState

data class HomeUiState(
    val showLoading: Boolean = false,
    val listCharacters: List<CharacterEntity>? = null
) : UIState
