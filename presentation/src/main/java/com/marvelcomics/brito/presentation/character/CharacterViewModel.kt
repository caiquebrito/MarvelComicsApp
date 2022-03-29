package com.marvelcomics.brito.presentation.character

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marvelcomics.brito.domain.exception.NetworkException
import com.marvelcomics.brito.domain.usecase.CharacterUseCase
import com.marvelcomics.brito.presentation.HomeState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CharacterViewModel(
    private val characterUseCase: CharacterUseCase,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private var _characterUiState = MutableStateFlow<Any>(HomeState.Empty)
    var characterUiState: StateFlow<Any> = _characterUiState

    fun loadCharacter(name: String) = viewModelScope.launch(dispatcher) {
        _characterUiState.value = HomeState.Loading
        characterUseCase.getCharacters(name)
            .catch {
                if (it is NetworkException) {
                    _characterUiState.value = HomeState.NetworkError
                } else {
                    _characterUiState.value = HomeState.Error(it)
                }
            }
            .collect {
                _characterUiState.value = HomeState.Success(it)
            }
    }
}
