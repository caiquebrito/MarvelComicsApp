package com.marvelcomics.brito.presentation.search

import androidx.lifecycle.viewModelScope
import com.marvelcomics.brito.domain.usecase.LoadCharacterUseCase
import com.marvelcomics.brito.domain.usecase.SaveCharacterUseCase
import com.marvelcomics.brito.domain.usecase.onFailure
import com.marvelcomics.brito.domain.usecase.onSuccess
import com.marvelcomics.brito.entity.CharacterEntity
import com.marvelcomics.brito.presentation.flow.ViewModel
import kotlinx.coroutines.launch

class SearchViewModel(
    private val loadCharacterUseCase: LoadCharacterUseCase,
    private val saveCharacterUseCase: SaveCharacterUseCase
) :
    ViewModel<SearchUiState, SearchUiEffect>(
        SearchUiState(
            isIdle = true,
            showLoading = false,
            listCharacters = null
        )
    ) {

    private var listIds: List<Int>? = null

    fun setListIds(ids: List<Int>) {
        listIds = ids
    }

    fun searchCharacterByName(name: String) {
        viewModelScope.launch {
            setState { state ->
                state.copy(isIdle = false, showLoading = true)
            }
            var listCharacters: List<CharacterEntity>? = null
            loadCharacterUseCase.invoke(name)
                .onSuccess {
                    listCharacters = it
                }.onFailure {
                    sendEffect(SearchUiEffect.ShowError)
                }
            setState {
                SearchUiState(
                    showLoading = false,
                    listCharacters = listCharacters
                )
            }
        }
    }

    fun addCharacterButtonClicked(characterEntity: CharacterEntity) {
        viewModelScope.launch {
            listIds?.let { ids ->
                if (ids.contains(characterEntity.id)) {
                    sendEffect(SearchUiEffect.ShowAlreadyAddedError)
                } else {
                    saveCharacterOnDatabase(characterEntity)
                }
            } ?: saveCharacterOnDatabase(characterEntity)
        }
    }

    private suspend fun saveCharacterOnDatabase(characterEntity: CharacterEntity) {
        saveCharacterUseCase.invoke(characterEntity)
            .onSuccess {
                sendEffect(SearchUiEffect.BackToHome)
            }.onFailure {
                sendEffect(SearchUiEffect.ShowError)
            }
    }
}
