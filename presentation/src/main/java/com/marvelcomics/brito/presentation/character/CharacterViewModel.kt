package com.marvelcomics.brito.presentation.character

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marvelcomics.brito.domain.exception.NetworkException
import com.marvelcomics.brito.domain.usecase.CharacterUseCase
import com.marvelcomics.brito.domain.usecase.CoroutineUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

@InternalCoroutinesApi
class CharacterViewModel(
    private val characterUseCase: CharacterUseCase
) : ViewModel() {

    private val interactions = Channel<CharacterInteraction>()
    private var _characterUiState =
        MutableStateFlow<CharacterScreenState>(CharacterScreenState.Empty)
    var characterUiState: StateFlow<CharacterScreenState> = _characterUiState

    init {
        viewModelScope.launch {
            interactions.consumeAsFlow().collect {
                handleInteraction(it)
            }
        }
    }

    private suspend fun handleInteraction(interaction: CharacterInteraction) {
        when (interaction) {
            is CharacterInteraction.SearchCharacter -> {
                characterUseCase.invoke(interaction.name).let {
                    when (it) {
                        is CoroutineUseCase.Result.Success -> {
                            _characterUiState.value = CharacterScreenState.Success(it)
                        }
                        is CoroutineUseCase.Result.Failure -> {
                            it.error?.let { throwable ->
                                if (throwable is NetworkException) {
                                    _characterUiState.value = CharacterScreenState.NetworkError
                                } else {
                                    _characterUiState.value = CharacterScreenState.Error(throwable)
                                }
                            } ?: apply {
                                _characterUiState.value =
                                    CharacterScreenState.Error(Exception("Not Mapped Error"))
                            }
                        }
                    }
                }
            }
        }
    }

    fun handle(interaction: CharacterInteraction) {
        viewModelScope.launch {
            interactions.send(interaction)
        }
    }
}
