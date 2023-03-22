package com.marvelcomics.brito.presentation.home

import androidx.lifecycle.viewModelScope
import com.marvelcomics.brito.domain.usecase.LoadAllCharactersIdsUseCase
import com.marvelcomics.brito.domain.usecase.LoadAllCharactersUseCase
import com.marvelcomics.brito.domain.usecase.models.onFailure
import com.marvelcomics.brito.domain.usecase.models.onSuccess
import com.marvelcomics.brito.entity.CharacterEntity
import com.marvelcomics.brito.presentation.flow.ViewModel
import kotlinx.coroutines.launch

class HomeViewModel(
    private val loadAllCharactersUseCase: LoadAllCharactersUseCase,
    private val loadAllCharactersIdsUseCase: LoadAllCharactersIdsUseCase
) :
    ViewModel<HomeUiState, HomeUiEffect>(HomeUiState(showLoading = true)) {

    fun getLocalCharacters() {
        viewModelScope.launch {
            setState { state ->
                state.copy(showLoading = true)
            }
            var listCharacters: List<CharacterEntity>? = null
            loadAllCharactersUseCase.invoke()
                .onSuccess {
                    listCharacters = it
                }.onFailure {
                    sendEffect(HomeUiEffect.ShowError)
                    sendEffect(HomeUiEffect.ShowError)
                }
            setState {
                HomeUiState(showLoading = false, listCharacters = listCharacters)
            }
        }
    }

    private fun getLocalCharactersIds() {
        viewModelScope.launch {
            var listIds: List<Int>? = null
            loadAllCharactersIdsUseCase.invoke()
                .onSuccess {
                    listIds = it
                }.onFailure {
                }
            sendOpenSearchScreenEffect(ids = listIds)
        }
    }

    private fun sendOpenSearchScreenEffect(ids: List<Int>?) {
        sendEffect(HomeUiEffect.OpenSearchScreen(ids))
    }

    fun emptyButtonItemClicked() {
        sendOpenSearchScreenEffect(ids = null)
    }

    fun searchButtonClicked() {
        getLocalCharactersIds()
    }

    fun adapterItemClicked(entity: CharacterEntity) {
        sendEffect(HomeUiEffect.OpenDetailScreen(entity))
    }
}
