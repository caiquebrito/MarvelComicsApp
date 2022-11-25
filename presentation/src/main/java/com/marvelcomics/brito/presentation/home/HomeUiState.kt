package com.marvelcomics.brito.presentation.home

import br.com.cora.common.viewmodel.flow.UIState

data class HomeUiState(
    val showLoading: Boolean = false
) : UIState
