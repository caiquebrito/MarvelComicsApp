package com.marvelcomics.brito.presentation.character

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marvelcomics.brito.domain.exception.NetworkException
import com.marvelcomics.brito.domain.usecase.CharacterUseCase
import com.marvelcomics.brito.presentation.CharacterUiState
import com.marvelcomics.brito.presentation.GlobalUiState
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

    private var _characterUiState = MutableStateFlow<Any>(GlobalUiState.Empty)
    var characterUiState: StateFlow<Any> = _characterUiState

    fun loadCharacter(name: String) = viewModelScope.launch(dispatcher) {
        _characterUiState.value = GlobalUiState.Loading
        characterUseCase.getCharacters(name)
            .catch {
                if (it is NetworkException) {
                    _characterUiState.value = GlobalUiState.NetworkError
                } else {
                    _characterUiState.value = CharacterUiState.Error(it)
                }
            }
            .collect {
                _characterUiState.value = CharacterUiState.Success(it)
            }
    }
}
