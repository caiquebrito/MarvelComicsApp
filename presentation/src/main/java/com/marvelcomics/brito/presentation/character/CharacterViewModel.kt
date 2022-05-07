package com.marvelcomics.brito.presentation.character

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marvelcomics.brito.domain.exception.NetworkException
import com.marvelcomics.brito.domain.usecase.CharacterUseCase
import com.marvelcomics.brito.domain.usecase.onFailure
import com.marvelcomics.brito.domain.usecase.onSuccess
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

@InternalCoroutinesApi
class CharacterViewModel(
    private val characterUseCase: CharacterUseCase
) : ViewModel() {

    private val interactions = Channel<CharacterInteraction>()
    private var characterUiState =
        MutableStateFlow<CharacterScreenState>(CharacterScreenState.Empty)

    fun bind() = characterUiState.asStateFlow()

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
                characterUiState.emit(CharacterScreenState.Loading)
                characterUseCase.invoke(interaction.name)
                    .onSuccess {
                        characterUiState.value = CharacterScreenState.Success(it)
                    }.onFailure { throwable ->
                        if (throwable is NetworkException) {
                            characterUiState.value = CharacterScreenState.NetworkError
                        } else {
                            characterUiState.value = CharacterScreenState.Error(throwable)
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
