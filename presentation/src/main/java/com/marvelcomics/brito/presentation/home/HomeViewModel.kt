package com.marvelcomics.brito.presentation.home

import androidx.lifecycle.viewModelScope
import br.com.cora.common.viewmodel.flow.ViewModel
import kotlinx.coroutines.launch

class HomeViewModel() : ViewModel<HomeUiState, HomeUiEffect>(HomeUiState(showLoading = true)) {

    fun getHeroesLocal() {
        viewModelScope.launch {
        }
        setState { state ->
            state.copy(showLoading = false)
        }
    }
}
