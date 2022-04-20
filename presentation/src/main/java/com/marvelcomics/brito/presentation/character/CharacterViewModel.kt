package com.marvelcomics.brito.presentation.character

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marvelcomics.brito.domain.exception.NetworkException
import com.marvelcomics.brito.domain.usecase.CharacterUseCase
import com.marvelcomics.brito.domain.usecase.CoroutineUseCase
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
        characterUseCase.invoke(name).let {
            when (it) {
                is CoroutineUseCase.Result.Success -> {
                    _characterUiState.value = HomeState.Success(it)
                }
                is CoroutineUseCase.Result.Failure -> {
                    it.error?.let { throwable ->
                        if (throwable is NetworkException) {
                            _characterUiState.value = HomeState.NetworkError
                        } else {
                            _characterUiState.value = HomeState.Error(throwable)
                        }
                    } ?: apply {
                        _characterUiState.value = HomeState.Error(Exception("Not Mapped Error"))
                    }
                }
            }
        }
    }
}
