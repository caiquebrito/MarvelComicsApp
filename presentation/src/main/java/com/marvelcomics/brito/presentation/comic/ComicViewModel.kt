package com.marvelcomics.brito.presentation.comic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marvelcomics.brito.domain.exception.NetworkException
import com.marvelcomics.brito.domain.usecase.ComicUseCase
import com.marvelcomics.brito.domain.usecase.CoroutineUseCase
import com.marvelcomics.brito.presentation.character.CharacterScreenState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class ComicViewModel(
    private val comicUseCase: ComicUseCase
) : ViewModel() {

    private val interactions = Channel<ComicInteraction>()
    private var _comicUiState = MutableStateFlow<Any>(CharacterScreenState.Empty)
    val comicUiState: StateFlow<Any> = _comicUiState

    init {
        viewModelScope.launch {
            interactions.consumeAsFlow().collect {
                handleInteraction(it)
            }
        }
    }

    private suspend fun handleInteraction(interaction: ComicInteraction) {
        when (interaction) {
            is ComicInteraction.LoadComicsById -> {
                _comicUiState.value = CharacterScreenState.Loading

                comicUseCase.invoke(interaction.id).let {
                    when (it) {
                        is CoroutineUseCase.Result.Success -> {
                            _comicUiState.value = ComicScreenState.Success(it)
                        }
                        is CoroutineUseCase.Result.Failure -> {
                            it.error?.let { throwable ->
                                if (throwable is NetworkException) {
                                    _comicUiState.value = ComicScreenState.NetworkError
                                } else {
                                    _comicUiState.value = ComicScreenState.Error(throwable)
                                }
                            } ?: apply {
                                _comicUiState.value =
                                    CharacterScreenState.Error(Exception("Not Mapped Error"))
                            }
                        }
                    }
                }
            }
        }
    }

    fun handle(interaction: ComicInteraction) {
        viewModelScope.launch {
            interactions.send(interaction)
        }
    }
}
