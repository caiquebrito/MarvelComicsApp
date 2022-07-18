package com.marvelcomics.brito.presentation.series

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marvelcomics.brito.domain.exception.NetworkException
import com.marvelcomics.brito.domain.usecase.Series
import com.marvelcomics.brito.domain.usecase.onFailure
import com.marvelcomics.brito.domain.usecase.onSuccess
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

@InternalCoroutinesApi
class SeriesViewModel(
    private val series: Series
) : ViewModel() {

    private val interactions = Channel<SeriesInteraction>()
    private var seriesUiState = MutableStateFlow<Any>(SeriesScreenState.Empty)

    fun bind() = seriesUiState.asStateFlow()

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
                seriesUiState.value = SeriesScreenState.Loading
                series.invoke(interaction.id)
                    .onSuccess {
                        seriesUiState.value = SeriesScreenState.Success(it)
                    }.onFailure { throwable ->
                        if (throwable is NetworkException) {
                            seriesUiState.value = SeriesScreenState.NetworkError
                        } else {
                            seriesUiState.value = SeriesScreenState.Error(throwable)
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
