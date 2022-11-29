package com.marvelcomics.brito.presentation.search

import androidx.lifecycle.viewModelScope
import com.marvelcomics.brito.domain.models.CharacterDomain
import com.marvelcomics.brito.domain.usecase.LoadCharacterUseCase
import com.marvelcomics.brito.domain.usecase.onFailure
import com.marvelcomics.brito.domain.usecase.onSuccess
import com.marvelcomics.brito.presentation.flow.ViewModel
import kotlinx.coroutines.launch

class SearchViewModel(private val loadCharacterUseCase: LoadCharacterUseCase) :
    ViewModel<SearchUiState, SearchUiEffect>(SearchUiState(false, null)) {

    fun searchCharacterByName(name: String) {
        viewModelScope.launch {
            var listCharacters: List<CharacterDomain>? = null
            loadCharacterUseCase.invoke(name)
                .onSuccess {
                    listCharacters = it
                }
                .onFailure {
                    sendEffect(SearchUiEffect.ShowEmptyCharacters)
                }
            setState { state ->
                state.copy(
                    showLoading = false,
                    listCharacters = listCharacters
                )
            }
        }
    }
}
