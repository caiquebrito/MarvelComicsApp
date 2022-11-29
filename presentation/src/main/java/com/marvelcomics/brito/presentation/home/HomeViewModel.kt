package com.marvelcomics.brito.presentation.home

import androidx.lifecycle.viewModelScope
import com.marvelcomics.brito.domain.models.CharacterDomain
import com.marvelcomics.brito.domain.usecase.LoadAllCharactersUseCase
import com.marvelcomics.brito.domain.usecase.onFailure
import com.marvelcomics.brito.domain.usecase.onSuccess
import com.marvelcomics.brito.presentation.flow.ViewModel
import kotlinx.coroutines.launch

class HomeViewModel(val loadAllCharactersUseCase: LoadAllCharactersUseCase) :
    ViewModel<HomeUiState, HomeUiEffect>(HomeUiState(showLoading = true)) {

    fun getHeroesLocal() {
        viewModelScope.launch {
            var listCharacters: List<CharacterDomain>? = null
            loadAllCharactersUseCase.invoke()
                .onSuccess {
                    listCharacters = it
                }.onFailure {
                    sendEffect(HomeUiEffect.ShowEmptyCharacters)
                }
            setState { state ->
                state.copy(
                    showLoading = false,
                    listCharacters = listCharacters
                )
            }
        }
    }

    fun openSearchScreen() {
        viewModelScope.launch {
            sendEffect(HomeUiEffect.OpenSearchScreen)
        }
    }
}
