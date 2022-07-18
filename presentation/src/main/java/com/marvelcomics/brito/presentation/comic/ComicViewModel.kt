package com.marvelcomics.brito.presentation.comic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marvelcomics.brito.domain.exception.NetworkException
import com.marvelcomics.brito.domain.usecase.Comic
import com.marvelcomics.brito.domain.usecase.onFailure
import com.marvelcomics.brito.domain.usecase.onSuccess
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

@InternalCoroutinesApi
class ComicViewModel(
    private val comic: Comic
) : ViewModel() {

    private val interactions = Channel<ComicInteraction>()
    private var comicUiState = MutableStateFlow<Any>(ComicScreenState.Empty)

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
                comicUiState.emit(ComicScreenState.Loading)
                comic.invoke(interaction.id)
                    .onSuccess {
                        comicUiState.value = ComicScreenState.Success(it)
                    }
                    .onFailure { throwable ->
                        if (throwable is NetworkException) {
                            comicUiState.value = ComicScreenState.NetworkError
                        } else {
                            comicUiState.value = ComicScreenState.Error(throwable)
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
