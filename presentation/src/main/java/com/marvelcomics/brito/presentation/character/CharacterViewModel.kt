package com.marvelcomics.brito.presentation.character

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marvelcomics.brito.domain.exception.NetworkException
import com.marvelcomics.brito.domain.usecase.Character
import com.marvelcomics.brito.domain.usecase.LoadLastCharacter
import com.marvelcomics.brito.domain.usecase.SaveCharacter
import com.marvelcomics.brito.domain.usecase.onFailure
import com.marvelcomics.brito.domain.usecase.onSuccess
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class CharacterViewModel(
    private val character: Character,
    private val loadLastCharacter: LoadLastCharacter,
    private val saveCharacter: SaveCharacter,
    scope: CoroutineScope?
) : ViewModel() {

    private var mainScope = scope ?: viewModelScope
    private val interactions = Channel<CharacterInteraction>()
    private var characterUiState = MutableSharedFlow<CharacterScreenState>()

    fun bind() = characterUiState.asSharedFlow()

    init {
        mainScope.launch {
            interactions.consumeAsFlow().collect {
                handleInteraction(it)
            }
        }
    }

    private suspend fun handleInteraction(interaction: CharacterInteraction) {
        when (interaction) {
            is CharacterInteraction.SearchCharacter -> {
                characterUiState.emit(CharacterScreenState.Loading)
                character.invoke(interaction.name)
                    .onSuccess { characterDomain ->
                        mainScope.launch {
                            saveCharacter.invoke(characterDomain)
                            characterUiState.emitOnScope(
                                mainScope, CharacterScreenState.Success(characterDomain)
                            )
                        }
                    }.onFailure { throwable ->
                        mainScope.launch {
                            if (throwable is NetworkException) {
                                characterUiState.emitOnScope(
                                    mainScope, CharacterScreenState.NetworkError
                                )
                            } else {
                                characterUiState.emitOnScope(
                                    mainScope, CharacterScreenState.Error(throwable)
                                )
                            }
                        }
                    }
            }
            CharacterInteraction.LoadLastCharacter -> {
                characterUiState.emit(CharacterScreenState.Loading)
                loadLastCharacter.invoke()
                    .onSuccess { characterDomain ->
                        mainScope.launch {
                            characterUiState.emitOnScope(
                                mainScope, CharacterScreenState.Success(characterDomain)
                            )
                        }
                    }.onFailure { throwable ->
                        mainScope.launch {
                            characterUiState.emitOnScope(
                                mainScope,
                                CharacterScreenState.Error(
                                    throwable,
                                    "Can't Load from Local"
                                )
                            )
                        }
                    }
            }
        }
    }

    fun handle(interaction: CharacterInteraction) {
        mainScope.launch {
            interactions.send(interaction)
        }
    }
}

fun <T> MutableSharedFlow<T>.emitOnScope(scope: CoroutineScope, value: T) {
    val mutable = this
    scope.launch {
        mutable.emit(value)
    }
}
