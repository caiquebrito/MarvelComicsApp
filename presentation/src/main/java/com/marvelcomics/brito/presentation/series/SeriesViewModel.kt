package com.marvelcomics.brito.presentation.series

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marvelcomics.brito.domain.exception.NetworkException
import com.marvelcomics.brito.domain.usecase.CoroutineUseCase
import com.marvelcomics.brito.domain.usecase.SeriesUseCase
import com.marvelcomics.brito.presentation.character.CharacterScreenState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class SeriesViewModel(
    private val seriesUseCase: SeriesUseCase
) : ViewModel() {

    private val interactions = Channel<SeriesInteraction>()
    private var _seriesUiState = MutableStateFlow<Any>(CharacterScreenState.Empty)

    fun bind() = _seriesUiState.asStateFlow()

    init {
        viewModelScope.launch {
            interactions.consumeAsFlow().collect {
                handleInteractions(it)
            }
        }
    }

    private suspend fun handleInteractions(interaction: SeriesInteraction) {
        when (interaction) {
            is SeriesInteraction.LoadSeriesById -> {
                _seriesUiState.value = SeriesScreenState.Loading
                seriesUseCase.invoke(interaction.id).let {
                    when (it) {
                        is CoroutineUseCase.Result.Success -> {
                            _seriesUiState.value = SeriesScreenState.Success(it)
                        }
                        is CoroutineUseCase.Result.Failure -> {
                            it.error?.let { throwable ->
                                if (throwable is NetworkException) {
                                    _seriesUiState.value = SeriesScreenState.NetworkError
                                } else {
                                    _seriesUiState.value = SeriesScreenState.Error(throwable)
                                }
                            } ?: apply {
                                _seriesUiState.value =
                                    SeriesScreenState.Error(Exception("Not Mapped Error"))
                            }
                        }
                    }
                }
            }
        }
    }

    fun handle(interaction: SeriesInteraction) {
        viewModelScope.launch {
            interactions.send(interaction)
        }
    }
}
