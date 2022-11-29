package com.marvelcomics.brito.presentation.search

import com.marvelcomics.brito.domain.models.CharacterDomain
import com.marvelcomics.brito.presentation.flow.UIState

data class SearchUiState(
    val showLoading: Boolean,
    val listCharacters: List<CharacterDomain>?
) : UIState
