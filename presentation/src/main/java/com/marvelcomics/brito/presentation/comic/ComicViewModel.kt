package com.marvelcomics.brito.presentation.comic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marvelcomics.brito.domain.exception.NetworkException
import com.marvelcomics.brito.domain.usecase.ComicUseCase
import com.marvelcomics.brito.domain.usecase.CoroutineUseCase
import com.marvelcomics.brito.presentation.character.CharacterScreenState
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

@InternalCoroutinesApi
class ComicViewModel(
    private val comicUseCase: ComicUseCase
) : ViewModel() {

    private val interactions = Channel<ComicInteraction>()
    private var comicUiState = MutableStateFlow<Any>(CharacterScreenState.Empty)

    fun bind() = comicUiState.asStateFlow()

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
                comicUiState.emit(CharacterScreenState.Loading)
                comicUseCase.invoke(interaction.id).let {
                    when (it) {
                        is CoroutineUseCase.Result.Success -> {
                            comicUiState.emit(ComicScreenState.Success(it))
                        }
                        is CoroutineUseCase.Result.Failure -> {
                            it.error?.let { throwable ->
                                if (throwable is NetworkException) {
                                    comicUiState.emit(ComicScreenState.NetworkError)
                                } else {
                                    comicUiState.emit(ComicScreenState.Error(throwable))
                                }
                            } ?: apply {
                                comicUiState.emit(CharacterScreenState.Error(Exception("Not Mapped Error")))
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
