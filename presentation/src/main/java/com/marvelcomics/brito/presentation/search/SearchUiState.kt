package com.marvelcomics.brito.presentation.search

import com.marvelcomics.brito.entity.CharacterEntity
import com.marvelcomics.brito.presentation.flow.UIState

data class SearchUiState(
    val isIdle: Boolean = false,
    val showLoading: Boolean,
    val listCharacters: List<CharacterEntity>?
) : UIState
