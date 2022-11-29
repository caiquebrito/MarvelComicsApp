package com.marvelcomics.brito.presentation.home

import com.marvelcomics.brito.domain.models.CharacterDomain
import com.marvelcomics.brito.presentation.flow.UIState

data class HomeUiState(
    val showLoading: Boolean = false,
    val listCharacters: List<CharacterDomain>? = null
) : UIState
