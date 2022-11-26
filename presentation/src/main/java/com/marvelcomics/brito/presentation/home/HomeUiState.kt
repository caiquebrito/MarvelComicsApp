package com.marvelcomics.brito.presentation.home

import com.marvelcomics.brito.domain.models.CharacterDomain
import com.marvelcomics.brito.presentation.flow.UIState

data class HomeUiState(
    val showLoading: Boolean = false,
    val heroesInfo: List<CharacterDomain>? = null
) : UIState
