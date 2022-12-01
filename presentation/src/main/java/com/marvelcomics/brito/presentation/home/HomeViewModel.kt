package com.marvelcomics.brito.presentation.home

import androidx.lifecycle.viewModelScope
import com.marvelcomics.brito.domain.usecase.LoadAllCharactersUseCase
import com.marvelcomics.brito.domain.usecase.onFailure
import com.marvelcomics.brito.domain.usecase.onSuccess
import com.marvelcomics.brito.entity.CharacterEntity
import com.marvelcomics.brito.presentation.flow.ViewModel
import kotlinx.coroutines.launch

class HomeViewModel(val loadAllCharactersUseCase: LoadAllCharactersUseCase) :
    ViewModel<HomeUiState, HomeUiEffect>(HomeUiState(showLoading = true)) {

    fun getLocalCharacters() {
        viewModelScope.launch {
            setState {
                HomeUiState(showLoading = true, listCharacters = null)
            }
            var listCharacters: List<CharacterEntity>? = null
            loadAllCharactersUseCase.invoke()
                .onSuccess {
                    listCharacters = it
                }.onFailure {
                    sendEffect(HomeUiEffect.ShowError)
                }
            setState {
                HomeUiState(showLoading = false, listCharacters = listCharacters)
            }
        }
    }

    fun emptyButtonItemClicked() {
        sendOpenSearchScreenEffect()
    }

    fun searchButtonClicked() {
        sendOpenSearchScreenEffect()
    }

    private fun sendOpenSearchScreenEffect() {
        viewModelScope.launch {
            sendEffect(HomeUiEffect.OpenSearchScreen)
        }
    }
}
