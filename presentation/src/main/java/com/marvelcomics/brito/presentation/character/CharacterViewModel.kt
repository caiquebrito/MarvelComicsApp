package com.marvelcomics.brito.presentation.character

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marvelcomics.brito.domain.exception.NetworkException
import com.marvelcomics.brito.domain.usecase.CharacterUseCase
import com.marvelcomics.brito.domain.usecase.onFailure
import com.marvelcomics.brito.domain.usecase.onSuccess
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

@InternalCoroutinesApi
class CharacterViewModel(
    private val characterUseCase: CharacterUseCase,
    private val scope: CoroutineScope?
) : ViewModel() {

    private var mainScope = scope ?: viewModelScope
    private val interactions = Channel<CharacterInteraction>()
    private var characterUiState =
        MutableSharedFlow<CharacterScreenState>()

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
                characterUseCase.invoke(interaction.name)
                    .onSuccess {
                        mainScope.launch {
                            characterUiState.emitOnScope(
                                mainScope, CharacterScreenState.Success(it)
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
