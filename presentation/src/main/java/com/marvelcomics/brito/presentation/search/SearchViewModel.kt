package com.marvelcomics.brito.presentation.search

import android.util.Log
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
    ViewModel<SearchUiState, SearchUiEffect>(SearchUiState(false, null)) {

    fun searchCharacterByName(name: String) {
        viewModelScope.launch {
            Log.i("CoroutineTest", "Before execution")
            var listCharacters: List<CharacterEntity>? = null
            loadCharacterUseCase.invoke(name)
                .onSuccess {
                    Log.i("CoroutineTest", "Success execution")
                    listCharacters = it
                }.onFailure {
                    Log.i("CoroutineTest", "Failure execution")
                    sendEffect(SearchUiEffect.ShowError)
                }
            Log.i("CoroutineTest", "After execution")
            setState { state ->
                state.copy(
                    showLoading = false,
                    listCharacters = listCharacters
                )
            }
        }
    }

    fun addCharacterButtonClicked(characterEntity: CharacterEntity) {
        viewModelScope.launch {
            saveCharacterUseCase.invoke(characterEntity)
                .onSuccess {
                    sendEffect(SearchUiEffect.BackToHome)
                }.onFailure {
                    sendEffect(SearchUiEffect.ShowError)
                }
        }
    }
}
